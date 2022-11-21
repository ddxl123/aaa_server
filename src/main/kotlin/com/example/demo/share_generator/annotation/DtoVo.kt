package com.example.demo.share_generator.annotation

import com.example.demo.share_generator.generator.ClassTarget


@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class DtoVo(
        /**
         * [ClassTarget.dartRelativePath]
         */
        val dartRelativePath: String = "",
        /**
         * [ClassTarget.kotlinRelativePath]
         */
        val kotlinRelativePath: String = "",
)
