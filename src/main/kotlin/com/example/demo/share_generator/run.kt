package com.example.demo.share_generator

import com.example.demo.share_generator.client_table_generator.clientTableGeneratorRun
import com.example.demo.share_generator.common.typeSet
import com.example.demo.share_generator.dto_vo_generator.dtoVoGeneratorRun
import com.example.demo.share_generator.enum_genrator.enumGeneratorRun
import com.example.demo.share_generator.route_generator.routeGenerator
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.createDirectories

fun main() {
    val kotlinPackageName = "com.example.demo"
    val dartCommonLib = "D:/project/aaa/subpackages/drift_main/lib"
    val dartShareEnumImport = "import 'package:drift_main/share_common/share_enum.dart';"
    clientTableGeneratorRun(kotlinPackageName = kotlinPackageName, dartCommonLib = dartCommonLib, dartShareEnumImport = dartShareEnumImport)
    dtoVoGeneratorRun(kotlinPackageName = kotlinPackageName, dartCommonLib = dartCommonLib, dartShareEnumImport = dartShareEnumImport)
    enumGeneratorRun(dartCommonLib = dartCommonLib)
    routeGenerator(dartCommonLib = dartCommonLib, kotlinPackageName = kotlinPackageName)
}

