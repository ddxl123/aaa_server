package com.example.demo.share_generator.common

import com.example.demo.entity.base.BaseEntity
import com.example.demo.share_generator.dto_vo_generator.annotation.Bo
import java.lang.reflect.ParameterizedType
import java.time.Instant
import kotlin.reflect.KClass
import kotlin.reflect.full.*
import kotlin.reflect.jvm.internal.impl.load.kotlin.KotlinClassFinder.Result.KotlinClass
import kotlin.reflect.jvm.javaType
import kotlin.reflect.jvm.jvmName

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

/**
 * [isElementForceNullable] 只对 [kClass] 类型为 Array 时有效。
 */
fun getOrSetTypeTarget(kClass: KClass<*>, isElementForceNullable: Boolean? = null): TypeTarget {
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
    if (kClass.isSubclassOf(BaseEntity::class)) {
        val newTypeTarget = TypeTarget(
                kClass = kClass,
                dartType = TypeWithImport(typeName = kClass.simpleName!!.removeSuffix("s"), import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "", import = ""),
        )
        typeSet.add(newTypeTarget)
        return newTypeTarget
    }
    if (kClass.hasAnnotation<Bo>()) {
        val newTypeTarget = TypeTarget(
                kClass = kClass,
                dartType = TypeWithImport(typeName = kClass.simpleName!!, import = ""),
                dartDriftColumnType = TypeWithImport(typeName = "", import = ""),
                dartDriftInternalType = TypeWithImport(typeName = "", import = ""),
        )
        typeSet.add(newTypeTarget)
        return newTypeTarget
    }
    if (kClass.qualifiedName == "kotlin.Array") {
        val newTypeTarget = TypeTarget(
                kClass = kClass,
                dartType = TypeWithImport(
                        typeName = "List<${kClass.java.componentType.simpleName}${if (isElementForceNullable!!) "?" else ""}>",
                        import = "",
                ),
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
