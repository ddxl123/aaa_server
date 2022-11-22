package com.example.demo.share_generator.dto_vo_generator

import com.example.demo.share_generator.common.scanClasses
import com.example.demo.share_generator.dto_vo_generator.annotation.DtoVo
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.createDirectories

class DtoVoGenerator {
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
        var shareMainPath: String = ""

        private val targets = mutableListOf<ClassTarget>()

        fun run(
                kotlinPackageName: String,
                kotlinGeneratorRootPath: String,
                dartGeneratorRootPath: String,
                dartBaseObjectImport: String,
                shareMainPath: String,
        ) {
            Companion.kotlinPackageName = kotlinPackageName
            Companion.kotlinGeneratorRootPath = kotlinGeneratorRootPath
            Companion.dartGeneratorRootPath = dartGeneratorRootPath
            Companion.dartBaseObjectImport = dartBaseObjectImport
            Companion.shareMainPath = shareMainPath

            handleClassTarget()

            for (target in targets) {
                Path(target.kotlinCompletePath).createDirectories()
                Path(target.dartCompletePathNoClass).createDirectories()
                File(target.kotlinCompletePathWithClassSuffix).writeText(target.toKotlinContent())
                File(target.dartCompletePathWithClassAndSuffix).writeText(target.toDartContent())
            }
        }

        private fun handleClassTarget() {
            scanClasses(kotlinPackageName).forEach { kClass ->
                val dtoVoAnnotation = kClass.java.getAnnotation(DtoVo::class.java)
                if (dtoVoAnnotation != null) {
                    targets.addAll(
                            arrayListOf(
                                    ClassTarget(
                                            dtoVoAnnotation.dartRelativePath,
                                            dtoVoAnnotation.kotlinRelativePath,
                                            kClass.java,
                                            TargetClassType.Dto
                                    ),
                                    ClassTarget(
                                            dtoVoAnnotation.dartRelativePath,
                                            dtoVoAnnotation.kotlinRelativePath,
                                            kClass.java,
                                            TargetClassType.Vo
                                    )
                            )
                    )
                }
            }
        }
    }
}
