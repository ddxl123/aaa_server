package com.example.demo.share_generator.check

import com.example.demo.controller.CodeMessage
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.common.scanClasses
import com.example.demo.share_generator.dto_vo_generator.annotation.DtoVo
import com.example.demo.share_generator.kotlinPackageName
import javax.persistence.Column
import javax.persistence.Entity
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.staticFunctions
import kotlin.reflect.full.staticProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField

/**
 * 检查实体是否带后缀。
 */
fun checkEntityWithS() {
    val cls = scanClasses(kotlinPackageName = kotlinPackageName)
    cls.forEach { cl ->
        if (cl.java.getAnnotation(Entity::class.java) != null) {
            if (!cl.simpleName!!.endsWith("s")) {
                throw Exception("实体类的命名必须带有 s 后缀！${cl.qualifiedName}\nat ${cl.qualifiedName}(${cl.qualifiedName}.kt:0)")
            }
        }
    }
}

/**
 * 检查实体类 client_ 前缀字段的可空类型是否与 [Column] 可空类型是否匹配。
 *
 * 纯 client table 不需要检查(字段默认值可为 null)
 */
fun checkClientFieldDefaultNotNull() {
    val cls = scanClasses(kotlinPackageName = kotlinPackageName)
    cls.forEach { cl ->
        if (cl.java.getAnnotation(Entity::class.java) != null) {
            cl.memberProperties.forEach { m ->
                if (m.name.contains(Regex("^(client_)"))) {
                    val col = m.javaField!!.getAnnotation(Column::class.java)
                    if (m.returnType.isMarkedNullable != col.nullable) {
                        throw Exception("client_ 字段可空不匹配。${cl.simpleName + "." + m.name}\n" +
                                "at ${cl.qualifiedName}.${m.name}(${cl.qualifiedName}.kt:0)")
                    }
                }
            }
        }
    }
}

/**
 * 检查 codeXXX 是否重复。
 */
fun checkCodeRepeat() {
    val cls = scanClasses(kotlinPackageName = kotlinPackageName)
    val map = mutableMapOf<String, String>()
    cls.forEach { cl ->
        if (cl.java.getAnnotation(DtoVo::class.java) != null) {
            cl.java.declaredFields.map { m ->
                if (m.name.contains(Regex("^(code)([0-9]+)$"))) {
                    if (map.keys.contains(m.name)) {
                        throw Exception("codeXXX重复！${cl.qualifiedName}.${m.name} - ${map[m.name]}.${m.name}")
                    }
                    map[m.name] = cl.qualifiedName!!
                    m.isAccessible = true
                    if (m.name.removePrefix("code") != (m.get(null) as CodeMessage).code.toString()) {
                        throw Exception("字段名与字段值的code值不相同！${cl.qualifiedName}.${m.name}")
                    }
                }
            }
        }
    }

}