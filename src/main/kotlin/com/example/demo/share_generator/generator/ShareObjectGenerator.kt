package com.example.demo.share_generator.generator

import com.example.demo.share_generator.annotation.DtoVo
import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.core.io.support.ResourcePatternResolver
import org.springframework.core.type.classreading.CachingMetadataReaderFactory
import org.springframework.core.type.classreading.MetadataReaderFactory
import org.springframework.util.ClassUtils
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.createDirectories


class ShareObjectGenerator {
    companion object {


        /**
         * com.xxx.xxx
         */
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
         * dart 中每个 share_object 所继承的 BaseObject 的 import。
         *
         * 例如: import 'package:httper/BaseObject.dart';
         */
        var dartBaseObjectImport: String = ""

        /**
         * 相对根路径的主路径。
         */
        var mainPath: String = ""

        private val targets = mutableListOf<ClassTarget>()

        fun run(
                kotlinPackageName: String,
                kotlinGeneratorRootPath: String,
                dartGeneratorRootPath: String,
                dartBaseObjectImport: String,
                mainPath: String,
        ) {
            Companion.kotlinPackageName = kotlinPackageName
            Companion.kotlinGeneratorRootPath = kotlinGeneratorRootPath
            Companion.dartGeneratorRootPath = dartGeneratorRootPath
            Companion.dartBaseObjectImport = dartBaseObjectImport
            Companion.mainPath = mainPath

            handleClassTarget()

            for (target in targets) {
                Path(target.kotlinCompletePath).createDirectories()
                Path(target.dartCompletePathNoClass).createDirectories()
                File(target.kotlinCompletePathWithClassSuffix).writeText(target.toKotlinContent())
                File(target.dartCompletePathWithClassAndSuffix).writeText(target.toDartContent())
            }
        }

        private fun handleClassTarget() {
            //spring工具类，可以获取指定路径下的全部类
            val resourcePatternResolver: ResourcePatternResolver = PathMatchingResourcePatternResolver()


            val pattern: String = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    ClassUtils.convertClassNameToResourcePath("com.example.demo") + "/**/*.class"
            // 将会获取全部类。
            val resources = resourcePatternResolver.getResources(pattern)
            // MetadataReader 的工厂类
            val metadataReaderFactory: MetadataReaderFactory = CachingMetadataReaderFactory(resourcePatternResolver)
            resources.forEach {
                // 读取类信息
                val clas = Class.forName(metadataReaderFactory.getMetadataReader(it).classMetadata.className)
                val annotations = clas.getDeclaredAnnotationsByType(DtoVo::class.java)
                if (annotations.isNotEmpty()) {
                    val anno = annotations.first()
                    targets.addAll(
                            arrayListOf(
                                    ClassTarget(
                                            anno.dartRelativePath,
                                            anno.kotlinRelativePath,
                                            clas,
                                            TargetClassType.Dto
                                    ),
                                    ClassTarget(
                                            anno.dartRelativePath,
                                            anno.kotlinRelativePath,
                                            clas,
                                            TargetClassType.Vo
                                    )
                            )
                    )
                }
            }
        }
    }
}
