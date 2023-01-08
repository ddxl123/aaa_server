package com.example.demo.share_generator.dto_vo_generator

import com.example.demo.controller.dto_vo.DeviceAndTokenBo
import com.example.demo.entity.base.BaseEntity
import com.example.demo.share_generator.dto_vo_generator.annotation.Bo
import com.example.demo.tool.toLowercaseLine
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.KProperty1
import kotlin.reflect.full.*


/**
 * 对 XXX:id 类型的扩展函数。
 *
 * 参数需要与 [FieldTarget] 相同。
 *
 * dtos/vos 集合字段会比 [DtoVoGenerator.run] 配置的字段要先运行，因此在该 [toFieldTarget] 函数中是无法获取到 [DtoVoGenerator] 的值。
 */
fun <T, V> KProperty1<T, V>.toFieldTarget(
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
fun String.toFieldTarget(
        kotlinType: KClass<*>,
        isForceNullable: Boolean,
        explain: String = ""
): FieldTarget<Any, Any> {

    return FieldTarget(
            fieldName = this.toLowercaseLine(),
            kotlinType = kotlinType,
            isForceNullable = isForceNullable,
            explain = explain
    )
}

fun KClass<*>.toFieldTarget(
        isForceNullable: Boolean,
        explain: String = ""
): FieldTarget<Any, Any> {
    if (this.isSubclassOf(BaseEntity::class)) {
        return FieldTarget(
                fieldName = simpleName!!.removeSuffix("s").toLowercaseLine() + "_entity",
                kotlinType = this,
                isForceNullable = isForceNullable,
                explain = explain
        )
    }
    if (this.hasAnnotation<Bo>()) {
        return FieldTarget(
                fieldName = simpleName!!.toLowercaseLine(),
                kotlinType = this,
                isForceNullable = isForceNullable,
                explain = explain
        )
    }
    throw Exception("未处理类型: $qualifiedName")
}


inline fun <reified T : Any> KClass<T>.toListFieldTarget(
        isListForceNullable: Boolean,
        isElementForceNullable: Boolean,
        explain: String = ""
): FieldTarget<Any, Any> {
    return FieldTarget(
            fieldName = (this.simpleName!! + "List").toLowercaseLine(),
            // 这里必须用 arrayOf，而非 arrayListOf，否则识别不出来
            kotlinType = arrayOf<T>()::class,
            isElementForceNullable = isElementForceNullable,
            isForceNullable = isListForceNullable,
            explain = explain
    )
}

fun paddingFieldTarget(): FieldTarget<Any, Any> {
    return FieldTarget(
            fieldName = "padding",
            kotlinType = Boolean::class,
            isForceNullable = true,
            explain = "填充字段"
    )
}
