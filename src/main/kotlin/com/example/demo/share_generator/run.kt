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
import kotlin.reflect.full.superclasses
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField

const val kotlinPackageName = "com.example.demo"
const val kotlinGeneratorRootPath = "D:/project/aaa_server/src/main/kotlin/com/example/demo"
const val dartDriftMainLib = "D:/project/aaa/subpackages/drift_main/lib"
const val dartShareEnumImport = "import 'package:drift_main/share_common/share_enum.dart';"

fun main() {
    check()
    clientTableGeneratorRun(kotlinPackageName = kotlinPackageName, dartCommonLib = dartDriftMainLib, dartShareEnumImport = dartShareEnumImport)
    dtoVoGeneratorRun(kotlinPackageName = kotlinPackageName, kotlinGeneratorRootPath = kotlinGeneratorRootPath, dartCommonLib = dartDriftMainLib, dartShareEnumImport = dartShareEnumImport)
    otherResponseCodeGenerator(dartDriftMainLib = dartDriftMainLib)
    enumGeneratorRun(dartCommonLib = dartDriftMainLib)
    routeGenerator(dartCommonLib = dartDriftMainLib, kotlinPackageName = kotlinPackageName)
    crtRun(kotlinPackageName = kotlinPackageName, kotlinGeneratorRootPath = kotlinGeneratorRootPath)
    repositoryRun(kotlinPackageName = kotlinPackageName, kotlinGeneratorRootPath = kotlinGeneratorRootPath)
}

fun check() {
    val cls = scanClasses(kotlinPackageName = kotlinPackageName)
    cls.forEach { cl ->
        if (cl.java.getAnnotation(Entity::class.java) != null) {
            if (!cl.simpleName!!.endsWith("s")) {
                throw Exception("实体类的命名必须带有 s 后缀！${cl.qualifiedName}")
            }
        }
    }
}
