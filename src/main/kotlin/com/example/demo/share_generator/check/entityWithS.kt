package com.example.demo.share_generator.check

import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.common.scanClasses
import com.example.demo.share_generator.kotlinPackageName
import javax.persistence.Column
import javax.persistence.Entity
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField


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