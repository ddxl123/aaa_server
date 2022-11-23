package com.example.demo.share_generator.dto_vo_generator

fun dtoVoGeneratorRun(kotlinPackageName: String, dartCommonLib: String, dartShareEnumImport: String) {
    DtoVoGenerator.run(
            kotlinPackageName = kotlinPackageName,
            kotlinGeneratorRootPath = "D:/project/aaa_server/src/main/kotlin/com/example/demo",
            dartGeneratorRootCompletePath = "$dartCommonLib/httper",
            dartBaseObjectImport = "import 'package:drift_main/httper/BaseObject.dart';",
            dartShareEnumImport = dartShareEnumImport,
            shareMainPath = "/share_generate_result/dto_vo",
    )
}


