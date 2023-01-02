package com.example.demo.share_generator

import com.example.demo.server_generator.run.crtRun
import com.example.demo.server_generator.run.repositoryRun
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import com.example.demo.share_generator.client_table_generator.clientTableGeneratorRun
import com.example.demo.share_generator.common.scanClasses
import com.example.demo.share_generator.dto_vo_generator.dtoVoGeneratorRun
import com.example.demo.share_generator.enum_genrator.enumGeneratorRun
import com.example.demo.share_generator.other_response_code_generator.otherResponseCodeGenerator
import com.example.demo.share_generator.route_generator.routeGenerator
import javax.persistence.Column
import javax.persistence.Entity
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField

fun main() {
    val kotlinPackageName = "com.example.demo"
    val kotlinGeneratorRootPath = "D:/project/aaa_server/src/main/kotlin/com/example/demo"
    val dartDriftMainLib = "D:/project/aaa/subpackages/drift_main/lib"
    val dartShareEnumImport = "import 'package:drift_main/share_common/share_enum.dart';"
    check(kotlinPackageName = kotlinPackageName)
    clientTableGeneratorRun(kotlinPackageName = kotlinPackageName, dartCommonLib = dartDriftMainLib, dartShareEnumImport = dartShareEnumImport)
    dtoVoGeneratorRun(kotlinPackageName = kotlinPackageName, kotlinGeneratorRootPath = kotlinGeneratorRootPath, dartCommonLib = dartDriftMainLib, dartShareEnumImport = dartShareEnumImport)
    otherResponseCodeGenerator(dartDriftMainLib = dartDriftMainLib)
    enumGeneratorRun(dartCommonLib = dartDriftMainLib)
    routeGenerator(dartCommonLib = dartDriftMainLib, kotlinPackageName = kotlinPackageName)
    crtRun(kotlinPackageName = kotlinPackageName, kotlinGeneratorRootPath = kotlinGeneratorRootPath)
    repositoryRun(kotlinPackageName = kotlinPackageName, kotlinGeneratorRootPath = kotlinGeneratorRootPath)
}


fun check(kotlinPackageName: String) {
    val cls = scanClasses(kotlinPackageName = kotlinPackageName)
    cls.forEach { cl ->
        val e = cl.java.getAnnotation(Entity::class.java)
        val ct = cl.java.getAnnotation(ClientTable::class.java)
        if (e != null || ct != null) {
            cl.memberProperties.forEach { m ->
                val colAnno = m.javaField!!.getAnnotation(Column::class.java)
                if (colAnno == null) {
                    throw Exception("${cl.simpleName}.${m.name} 缺少 ${Column::class.simpleName} 注解")
                } else {
                    if (m.returnType.isMarkedNullable != colAnno.nullable) {
                        throw Exception("${cl.simpleName}.${m.name} 类型是否可空，与 ${Column::class.simpleName} 注解是否可空，不匹配")
                    }
                }
            }
        }
    }
}