package com.example.demo.dto_vo.generator

import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1


/**
 * 对 XXX:id 类型的扩展函数。
 *
 * 参数需要与 [FieldTarget] 相同。
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

/**
 * 对 [String] 类型的扩展函数。
 *
 * 参数需要与 [ClassTarget] 相同。
 */
fun <T, V> String.toClassTarget(vararg fieldTargets: FieldTarget<T, V>): ClassTarget<T, V> {
    return ClassTarget(className = this, *fieldTargets)
}