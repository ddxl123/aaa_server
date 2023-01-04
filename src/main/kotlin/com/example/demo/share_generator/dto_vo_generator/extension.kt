package com.example.demo.share_generator.dto_vo_generator

import com.example.demo.entity.Users
import com.example.demo.entity.base.BaseEntity
import com.example.demo.share_generator.common.getTypeTarget
import com.example.demo.tool.toLowercaseLine
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.kotlinProperty


/**
 * 对 XXX:id 类型的扩展函数。
 *
 * 参数需要与 [FieldTarget] 相同。
 *
 * dtos/vos 集合字段会比 [DtoVoGenerator.run] 配置的字段要先运行，因此在该 [toFieldTarget] 函数中是无法获取到 [DtoVoGenerator] 的值。
 */
fun <T, V> KMutableProperty1<T, V>.toFieldTarget(
        isForceNullable: Boolean? = null,
        explain: String = ""
): FieldTarget<T, V> {
    return FieldTarget(
            this,
            isForceNullable = isForceNullable,
            explain = explain
    )
}

/**
 * 对 String 类型的扩展函数。
 *
 * 参数需要与 [FieldTarget] 相同。
 *
 * dtos/vos 集合字段会比 [DtoVoGenerator.run] 配置的字段要先运行，因此在该 [toFieldTarget] 函数中是无法获取到 [DtoVoGenerator] 的值。
 */
fun <T, V> String.toFieldTarget(
        kotlinType: KClass<*>,
        isForceNullable: Boolean,
        explain: String = ""
): FieldTarget<T, V> {
    return FieldTarget(
            fieldName = this,
            kotlinType = kotlinType,
            isForceNullable = isForceNullable,
            explain = explain
    )
}

fun <T, V> BaseEntity.toFieldTarget(
        isForceNullable: Boolean,
        explain: String = ""
): FieldTarget<T, V> {
    return FieldTarget(
            fieldName = this::class.simpleName!!.removeSuffix("s").toLowercaseLine() + "_entity",
            kotlinType = this::class,
            isForceNullable = isForceNullable,
            explain = explain
    )
}
