package com.example.demo.share_generator.generator

import com.example.demo.share_generator.annotation.ClientColumn
import com.example.demo.share_generator.annotation.ClientTable
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.core.io.support.ResourcePatternResolver
import org.springframework.core.type.classreading.CachingMetadataReaderFactory
import org.springframework.core.type.classreading.MetadataReaderFactory
import org.springframework.util.ClassUtils
import java.io.File
import javax.persistence.Column
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.reflect.full.*
import kotlin.reflect.jvm.*

fun main() {
    ClientTableGenerator().run()
}

class ClientTableGenerator {
    /**
     * kotlin 包内的相对路径。
     */
    private val kotlinRelativeScanPath = "/entity"

    /**
     * kotlin 包名。
     */
    private val kotlinPackageName = "com.example.demo"

    /**
     * 生成 dart 文件的绝对路径。
     */
    private val dartCompletePathNoClass = "D:/project/aaa/subpackages/drift_main/lib/table/cloud_table"

    private val clientTableTargets = arrayListOf<ClientTableTarget>()

    fun run() {
        handleClassTarget()
        for (clientTableTarget in clientTableTargets) {
            Path(dartCompletePathNoClass + clientTableTarget.path).createDirectories()
            File(dartCompletePathNoClass + clientTableTarget.path + "/" + clientTableTarget.tableName + ".dart").writeText(clientTableTarget.toDartSingleTableContent())
        }
        Path(dartCompletePathNoClass).createDirectories()
        File("$dartCompletePathNoClass/drift_db_table_part.dart").writeText(driftDbTablePartContent())
    }

    private fun driftDbTablePartContent(): String {
        return """
library drift_db_table_part;

import 'package:drift/drift.dart';
import 'package:drift_custom/ReferenceTo.dart';

${
            fun(): String {
                var content = ""
                for (clientTableTarget in clientTableTargets) {
                    content += """
part '${clientTableTarget.path.removePrefix("/")}${
                        if (clientTableTarget.path.trim() == "/" || clientTableTarget.path.isBlank()) "" else "/"
                    }${clientTableTarget.tableName}.dart';
            """
                }
                return content
            }()
        }
const List<Type> cloudTableClasses = [
${
            fun(): String {
                var content = ""
                for (clientTableTarget in clientTableTargets) {
                    content += """
  ${clientTableTarget.tableName},
"""
                }
                return content
            }()
        }
];
        """
    }

    @OptIn(ExperimentalStdlibApi::class)
    private fun handleClassTarget() {
        //spring工具类，可以获取指定路径下的全部类
        val resourcePatternResolver: ResourcePatternResolver = PathMatchingResourcePatternResolver()


        val pattern: String = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                ClassUtils.convertClassNameToResourcePath(kotlinPackageName) + "/**/*.class"
        // 将会获取全部类。
        val resources = resourcePatternResolver.getResources(pattern)
        // MetadataReader 的工厂类
        val metadataReaderFactory: MetadataReaderFactory = CachingMetadataReaderFactory(resourcePatternResolver)
        resources.forEach {
            // 读取类信息
            val clas = Class.forName(metadataReaderFactory.getMetadataReader(it).classMetadata.className).kotlin
            // /xxx 转 .xxx
            val toClassName = ClassUtils.convertResourcePathToClassName(kotlinRelativeScanPath)
            if (clas.qualifiedName != null && clas.qualifiedName!!.contains(toClassName)) {
                // clas.annotations 只会获取到 kotlin 的注解，clas.java.annotations 只会获取到 java 的注解。
                val tableClassAnnotations = clas.findAnnotations(ClientTable::class)
                if (tableClassAnnotations.isNotEmpty()) {
                    val path = clas.qualifiedName!!.split("$kotlinPackageName$toClassName.").last().split(".")
                            .toMutableList().run {
                                removeLast()
                                "/" + this.joinToString("/")
                            }
                    val clientMemberTargets = arrayListOf<ClientMemberTarget>()
                    clas.memberProperties.forEach { memberProperty ->
                        // clas.annotations 只会获取到 kotlin 的注解，clas.java.annotations 只会获取到 java 的注解。
                        val clientColumnAnnotation = memberProperty.findAnnotation<ClientColumn>()
                        if (clientColumnAnnotation != null) {
                            val cmt = ClientMemberTarget(
                                    name = memberProperty.name,
                                    // 需要的是 kotlin 的类型(如 Int)，并非 java 类型(如 Integer/int)，
                                    typeTarget = getTypeTarget(memberProperty.returnType.toString().split(".").last().replace("?", "")),
                                    // clas.annotations 只会获取到 kotlin 的注解，clas.java.annotations 只会获取到 java 的注解。
                                    isNullable = memberProperty.javaField!!.getDeclaredAnnotationsByType(Column::class.java).firstOrNull()?.nullable
                                            ?: true,
                                    referenceTos = clientColumnAnnotation.referenceTo.map { ref -> ref.simpleName!! } as ArrayList<String>,
                            )
                            clientMemberTargets.add(cmt)
                        }
                    }
                    clientTableTargets.add(
                            ClientTableTarget(
                                    path = path,
                                    tableName = clas.simpleName!!,
                                    clientMemberTargets = clientMemberTargets
                            )
                    )

                }
            }
        }
    }


}

class ClientMemberTarget(
        val name: String,
        val typeTarget: TypeTarget,
        val isNullable: Boolean,
        val referenceTos: ArrayList<String>,
) {
    override fun toString(): String {
        return "(name: $name, typeTarget: $typeTarget, isNullable: $isNullable, referenceTos: $referenceTos)"
    }
}

class ClientTableTarget(
        /**
         * 相对 [ClientTableGenerator.kotlinRelativeScanPath] 的路径。
         */
        val path: String,
        val tableName: String,
        val clientMemberTargets: ArrayList<ClientMemberTarget>,
) {
    override fun toString(): String {
        return "path: $path\ntableName: $tableName\nclientMemberTargets: $clientMemberTargets"
    }

    fun toDartSingleTableContent(): String {
        return """
part of drift_db_table_part;

@ReferenceTo([])
class $tableName extends Table {
${
            fun(): String {
                var content = ""
                for (clientMemberTarget in clientMemberTargets) {
                    if (clientMemberTarget.referenceTos.isNotEmpty()) {
                        content += """
  @ReferenceTo([${clientMemberTarget.referenceTos.joinToString(", ").removeSuffix(",")}])"""
                    }
                    content += """
  ${clientMemberTarget.typeTarget.dartDriftColumnType.typeName} get ${clientMemberTarget.name} => ${clientMemberTarget.typeTarget.dartDriftInternalType.typeName}()${
                        if (clientMemberTarget.isNullable) ".nullable()" else ""
                    }();
"""
                }
                return content
            }()
        }
}
        """
    }
}
