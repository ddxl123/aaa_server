package com.example.demo.share_generator.dto_vo_generator

import com.example.demo.share_generator.common.TypeTarget
import com.example.demo.share_generator.common.getTypeTarget
import com.example.demo.share_generator.common.typeSet
import javax.persistence.Column
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.jvm.javaField
import kotlin.reflect.jvm.kotlinProperty


class FieldTarget<out T, out V> {

    /**
     * [fieldTargetObj] XXX:name
     *
     * [isForceNullable] 是否强制为空或不为空，无论 XXX:name 的注解是否为空。
     * TODO: 注解为写业务
     * 当 [isForceNullable] 为 null 时，会根据 XXX:name 的注解自动检测是否为空。
     *
     * [explain] 当前变量的注释。
     */
    constructor(
            fieldTargetObj: KMutableProperty1<T, V>,
            isForceNullable: Boolean? = null,
            explain: String = ""
    ) {
        this.kotlinTypeName = fieldTargetObj.returnType.toString().split(".").last().replace("?", "")
        this.typeTarget = getTypeTarget(fieldTargetObj.javaField!!.type.kotlin)
        this.fieldName = fieldTargetObj.name.replace(Regex("[A-Z]")) { matchResult -> "_${matchResult.value.lowercase()}" }
        if (isForceNullable != null) {
            this.isForceNullable = isForceNullable
        } else {
            // 识别注解，解析出是否为空
            this.isForceNullable = fieldTargetObj.javaField!!.getDeclaredAnnotationsByType(Column::class.java).firstOrNull()?.nullable
                    ?: true
        }
        this.explain = explain
    }

    /**
     * [fieldName] 自命名的字段名。
     *
     * [kotlinType] 需要的 kotlin 的类型。
     * 可查看映射：[typeSet]
     *
     * [isForceNullable] 是否强制为空或不为空。
     *
     * [explain] 当前变量的注释。
     */
    constructor(
            fieldName: String,
            kotlinType: KClass<*>,
            isForceNullable: Boolean,
            explain: String = ""
    ) {
        this.kotlinTypeName = kotlinType.simpleName!!
        this.typeTarget = getTypeTarget(kotlinType)
        this.fieldName = fieldName
        this.isForceNullable = isForceNullable
        this.explain = explain
    }


    val fieldName: String
    val kotlinTypeName: String
    val typeTarget: TypeTarget
    val isForceNullable: Boolean
    val explain: String
}
