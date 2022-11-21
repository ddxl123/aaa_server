package com.example.demo.share_generator.generator

import java.time.Instant
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1

class TypeWithImport(
        val typeName: String,
        val import: String,
) {
    override fun toString(): String {
        return "(typeName: $typeName, import: $import)"
    }
}

class TypeTarget(
        val dartType: TypeWithImport,
        val dartDriftColumnType: TypeWithImport,
        val dartDriftInternalType: TypeWithImport,
        val kotlinTypeImport: String,
) {
    override fun toString(): String {
        return "(dartType: $dartType, dartDriftColumnType: $dartDriftColumnType, dartDriftInternalType: $dartDriftInternalType, kotlinTypeImport: $kotlinTypeImport)"
    }
}

/**
 * @return [String] 为类型名称，[TypeTarget] 为获取到的映射结果。
 */
fun getTypeTarget(simpleTypeName: String): TypeTarget {
    return typeMap[simpleTypeName] ?: throw Exception("未找到对应的 dartType！need:${simpleTypeName}")
}

val typeMap = mutableMapOf(
        Boolean::class.simpleName to TypeTarget(
                kotlinTypeImport = "",
                dartType = TypeWithImport(typeName = "bool", import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "BoolColumn", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "boolean", import = ""),

        ),
        /**
         * mysql - TINYINT
         */
        Byte::class.simpleName to TypeTarget(
                kotlinTypeImport = "",
                dartType = TypeWithImport(typeName = "int", import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "IntColumn", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "integer", import = ""),
        ),
        /**
         * mysql - SMALLINT
         */
        Short::class.simpleName to TypeTarget(
                kotlinTypeImport = "",
                dartType = TypeWithImport(typeName = "int", import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "IntColumn", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "integer", import = ""),
        ),
        /**
         * mysql - INT
         */
        Int::class.simpleName to TypeTarget(
                kotlinTypeImport = "",
                dartType = TypeWithImport(typeName = "int", import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "IntColumn", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "integer", import = ""),
        ),
        /**
         * mysql - BIGINT
         */
        Long::class.simpleName to TypeTarget(
                kotlinTypeImport = "",
                dartType = TypeWithImport(typeName = "int", import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "IntColumn", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "integer", import = ""),
        ),
        /**
         * mysql - DOUBLE
         */
        Double::class.simpleName to TypeTarget(
                kotlinTypeImport = "",
                dartType = TypeWithImport(typeName = "double", import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "RealColumn", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "real", import = ""),
        ),

        /**
         * mysql - CHAR
         */
        Char::class.simpleName to TypeTarget(
                kotlinTypeImport = "",
                dartType = TypeWithImport(typeName = "String", import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "TextColumn", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "text", import = ""),
        ),
        /**
         * mysql - VARCHAR
         */
        String::class.simpleName to TypeTarget(
                kotlinTypeImport = "",
                dartType = TypeWithImport(typeName = "String", import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "TextColumn", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "text", import = ""),
        ),
        /**
         * mysql - DATETIME
         */
        Instant::class.simpleName to TypeTarget(
                kotlinTypeImport = "import java.time.Instant",
                dartType = TypeWithImport(typeName = "DateTime", import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "DateTimeColumn", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "dateTime", import = ""),
        ),
)
