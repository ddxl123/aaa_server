package com.example.demo.generator

import java.io.File
import java.nio.file.*
import javax.persistence.Column
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.jvm.javaField


class FieldTarget<out T, out V> {

    /**
     * [fieldTargetObj] XXX:name
     *
     * [isForceNullable] 是否强制为空或不为空，无论 XXX:name 的注解是否为空。
     * 当 [isForceNullable] 为 null 时，会根据 XXX:name 自动检测是否为空。
     *
     * [explain] 当前变量的注释。
     */
    constructor(
            fieldTargetObj: KMutableProperty1<T, V>,
            isForceNullable: Boolean? = null,
            explain: String = ""
    ) {
        this.kotlinTypeName = fieldTargetObj.returnType.toString().split(".").last().replace("?", "")
        val targetValue: TargetValue = typeMap[this.kotlinTypeName]
                ?: throw Exception("未找到对应的 dartType！need:${this.kotlinTypeName}")
        this.fieldName = fieldTargetObj.name.replace(Regex("[A-Z]")) { matchResult -> "_${matchResult.value.lowercase()}" }
        this.dartTypeName = targetValue.dartTypeName
        this.kotlinImport = targetValue.kotlinImport
        this.dartImport = targetValue.dartImport
        if (isForceNullable != null) {
            this.isForceNullable = isForceNullable
        } else {
            // 识别注解，解析出是否为空
            this.isForceNullable = fieldTargetObj.javaField!!.getDeclaredAnnotationsByType(Column::class.java).firstOrNull()?.nullable
                    ?: true
        }
        this.explain = explain
    }

    /**
     * [fieldName] 自命名的字段名。
     *
     * [kotlinType] 需要的 kotlin 的类型。
     * 可查看映射：[typeMap]
     *
     * [isForceNullable] 是否强制为空或不为空。
     *
     * [explain] 当前变量的注释。
     */
    constructor(
            fieldName: String,
            kotlinType: KClass<*>,
            isForceNullable: Boolean,
            explain: String = ""
    ) {
        this.kotlinTypeName = kotlinType.simpleName!!
        val targetValue: TargetValue = typeMap[this.kotlinTypeName]
                ?: throw Exception("未找到对应的 dartType！need:${this.kotlinTypeName}")
        this.fieldName = fieldName
        this.dartTypeName = targetValue.dartTypeName
        this.kotlinImport = targetValue.kotlinImport
        this.dartImport = targetValue.dartImport
        this.isForceNullable = isForceNullable
        this.explain = explain
    }


    val fieldName: String
    val kotlinTypeName: String
    val dartTypeName: String
    val kotlinImport: String
    val dartImport: String
    val isForceNullable: Boolean
    val explain: String
}

class PathClass(
        /**
         * 相对 [dartGeneratorRootPath]+[mainPath] 的路径。
         */
        val dartRelativePath: String,

        /**
         * 相对 [kotlinGeneratorRootPath]+[mainPath] 的路径。
         */
        val kotlinRelativePath: String,

        /**
         * 统一类名。
         */
        val className: String

) {
    val dartCompletePathNoClass: String = ShareObjectGenerator.dartGeneratorRootPath + ShareObjectGenerator.mainPath + dartRelativePath
    val kotlinCompletePath: String = ShareObjectGenerator.kotlinGeneratorRootPath + ShareObjectGenerator.mainPath + kotlinRelativePath
    private val dartCompletePathWithClassNoClass: String
    private val kotlinCompletePathWithClass: String
    val dartCompletePathWithClassAndSuffix: String
    val kotlinCompletePathWithClassSuffix: String

    init {
        if (dartCompletePathNoClass.contains("\\") || kotlinCompletePath.contains("\\")) {
            throw Exception("路径必须用/，不要使用 \\")
        }
        dartCompletePathWithClassNoClass = "$dartCompletePathNoClass/$className"
        kotlinCompletePathWithClass = "$kotlinCompletePath/$className"
        dartCompletePathWithClassAndSuffix = "$dartCompletePathWithClassNoClass.dart"
        kotlinCompletePathWithClassSuffix = "$kotlinCompletePathWithClass.kt"
    }
}

class ClassTarget<out T, out V>(
        val pathClass: PathClass,
        private vararg val fieldTargets: FieldTarget<T, V>
) {
    private val kotlinImports = mutableSetOf<String>()

    fun toKotlinContent(): String {

        var fieldTargetContent = ""
        for (fieldTarget in fieldTargets) {
            kotlinImports.add(fieldTarget.kotlinImport)
            fieldTargetContent += """
    // ${fieldTarget.explain}
    var ${fieldTarget.fieldName}: ${fieldTarget.kotlinTypeName}${if (fieldTarget.isForceNullable) '?' else ""},${"\n"}"""
        }

        return """
package ${ShareObjectGenerator.kotlinPackageName}.${(ShareObjectGenerator.mainPath + pathClass.kotlinRelativePath).split(Regex("/")).run { subList(1, count()) }.joinToString(".")}
${kotlinImports.joinToString("\n")}
data class ${pathClass.className}(
$fieldTargetContent
)
""".trimIndent()
    }

    fun toDartContent(): String {
        var fieldTargetContent = ""
        var requiredContent = ""
        for (fieldTarget in fieldTargets) {
            fieldTargetContent += """
    /// ${fieldTarget.explain}
    ${fieldTarget.dartTypeName}${if (fieldTarget.isForceNullable) "?" else ""} ${fieldTarget.fieldName};
"""
            requiredContent += """
    required this.${fieldTarget.fieldName},
"""
        }
        return """
// ignore_for_file: non_constant_identifier_names
import 'package:json_annotation/json_annotation.dart';
part '${pathClass.className}.g.dart';
@JsonSerializable()
class ${pathClass.className} {
$fieldTargetContent
${pathClass.className}({
$requiredContent
});
    factory ${pathClass.className}.fromJson(Map<String, dynamic> json) => _$${pathClass.className}FromJson(json);
    Map<String, dynamic> toJson() => _$${pathClass.className}ToJson(this);
}
"""
    }
}

class ShareObjectGenerator {
    companion object {


        var kotlinPackageName: String = ""

        /**
         * kotlin 项目根路径。
         */
        var kotlinGeneratorRootPath: String = ""

        /**
         * dart 项目根路径是。
         */
        var dartGeneratorRootPath: String = ""

        /**
         * 相对根路径的主路径。
         */
        var mainPath: String = ""

        val targets = mutableListOf<ClassTarget<*, *>>()

        fun setConfig(
                kotlinPackageName: String,
                kotlinGeneratorRootPath: String,
                dartGeneratorRootPath: String,
                mainPath: String,
        ) {
            ShareObjectGenerator.kotlinPackageName = kotlinPackageName
            ShareObjectGenerator.kotlinGeneratorRootPath = kotlinGeneratorRootPath
            ShareObjectGenerator.dartGeneratorRootPath = dartGeneratorRootPath
            ShareObjectGenerator.mainPath = mainPath
        }

        fun run() {
            for (target in targets) {
                Path(target.pathClass.kotlinCompletePath).createDirectories()
                Path(target.pathClass.dartCompletePathNoClass).createDirectories()
                File(target.pathClass.kotlinCompletePathWithClassSuffix).writeText(target.toKotlinContent())
                File(target.pathClass.dartCompletePathWithClassAndSuffix).writeText(target.toDartContent())
            }
        }

    }
}
