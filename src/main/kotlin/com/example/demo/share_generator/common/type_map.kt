package com.example.demo.share_generator.common

import com.example.demo.entity.base.BaseEntity
import java.time.Instant
import kotlin.reflect.KClass
import kotlin.reflect.full.allSuperclasses
import kotlin.reflect.full.isSuperclassOf
import kotlin.reflect.full.superclasses

class TypeWithImport(
        val typeName: String,
        val import: String,
) {
    override fun toString(): String {
        return "(typeName: $typeName, import: $import)"
    }
}

class TypeTarget(
        val kClass: KClass<*>,
        val dartType: TypeWithImport,
        val dartDriftColumnType: TypeWithImport,
        val dartDriftInternalType: TypeWithImport,
) {
    override fun toString(): String {
        return "(dartType: $dartType, dartDriftColumnType: $dartDriftColumnType, dartDriftInternalType: $dartDriftInternalType)"
    }
}

fun getTypeTarget(kClass: KClass<*>): TypeTarget {
    for (typeTarget in typeSet) {
        if (typeTarget.kClass == kClass) {
            return typeTarget
        }
    }
    if (kClass.java.isEnum) {
        val newTypeTarget = TypeTarget(
                kClass = kClass,
                dartType = TypeWithImport(typeName = kClass.simpleName!!, import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "IntColumn", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "intEnum<${kClass.simpleName!!}>", import = ""),
        )
        typeSet.add(newTypeTarget)
        return newTypeTarget
    }
    if (kClass.allSuperclasses.contains(BaseEntity::class)) {
        val newTypeTarget = TypeTarget(
                kClass = kClass,
                dartType = TypeWithImport(typeName = kClass.simpleName!!.removeSuffix("s"), import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "", import = ""),
        )
        typeSet.add(newTypeTarget)
        return newTypeTarget
    }
    throw Exception("未找到对应的 dartType！need:${kClass.simpleName!!}")
}


val typeSet = mutableSetOf(
        /**
         * mysql - BOOLEAN
         */
        TypeTarget(
                kClass = Boolean::class,
                dartType = TypeWithImport(typeName = "bool", import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "BoolColumn", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "boolean", import = ""),
        ),
        /**
         * mysql - TINYINT
         */
        TypeTarget(
                kClass = Byte::class,
                dartType = TypeWithImport(typeName = "int", import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "IntColumn", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "integer", import = ""),
        ),
        /**
         * mysql - SMALLINT
         */
        TypeTarget(
                kClass = Short::class,
                dartType = TypeWithImport(typeName = "int", import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "IntColumn", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "integer", import = ""),
        ),
        /**
         * mysql - INT
         */
        TypeTarget(
                kClass = Int::class,
                dartType = TypeWithImport(typeName = "int", import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "IntColumn", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "integer", import = ""),
        ),
        /**
         * mysql - BIGINT
         */
        TypeTarget(
                kClass = Long::class,
                dartType = TypeWithImport(typeName = "int", import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "IntColumn", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "integer", import = ""),
        ),
        /**
         * mysql - DOUBLE
         */
        TypeTarget(
                kClass = Double::class,
                dartType = TypeWithImport(typeName = "double", import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "RealColumn", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "real", import = ""),
        ),
        /**
         * mysql - CHAR
         */
        TypeTarget(
                kClass = Char::class,
                dartType = TypeWithImport(typeName = "String", import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "TextColumn", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "text", import = ""),
        ),
        /**
         * mysql - VARCHAR
         */
        TypeTarget(
                kClass = String::class,
                dartType = TypeWithImport(typeName = "String", import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "TextColumn", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "text", import = ""),
        ),
        /**
         * mysql - DATETIME
         */
        TypeTarget(
                kClass = Instant::class,
                dartType = TypeWithImport(typeName = "DateTime", import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "DateTimeColumn", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "dateTime", import = ""),
        ),
)
