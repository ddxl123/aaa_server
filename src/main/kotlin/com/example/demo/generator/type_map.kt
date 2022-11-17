package com.example.demo.generator

import java.time.Instant

class TargetValue(
        val dartTypeName: String,
        val kotlinImport: String,
        val dartImport: String,
)

val typeMap = mutableMapOf(
        /**
         * mysql - TINYINT
         */
        Byte::class.simpleName to TargetValue("int", "", ""),
        /**
         * mysql - SMALLINT
         */
        Short::class.simpleName to TargetValue("int", "", ""),
        /**
         * mysql - INT
         */
        Int::class.simpleName to TargetValue("int", "", ""),
        /**
         * mysql - BIGINT
         */
        Long::class.simpleName to TargetValue("int", "", ""),
        /**
         * mysql - FLOAT
         */
        Float::class.simpleName to TargetValue("double", "", ""),

        /**
         * mysql - CHAR
         */
        Char::class.simpleName to TargetValue("String", "", ""),
        /**
         * mysql - VARCHAR
         */
        String::class.simpleName to TargetValue("String", "", ""),
        /**
         * mysql - DATETIME
         */
        Instant::class.simpleName to TargetValue("DateTime", "import java.time.Instant", ""),
)
