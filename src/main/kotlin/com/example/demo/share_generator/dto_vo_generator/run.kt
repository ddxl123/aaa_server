package com.example.demo.share_generator.dto_vo_generator

fun dtoVoGeneratorRun(kotlinPackageName: String, dartCommonLib: String) {
    DtoVoGenerator.run(
            kotlinPackageName = kotlinPackageName,
            kotlinGeneratorRootPath = "D:/project/aaa_server/src/main/kotlin/com/example/demo",
            dartGeneratorRootPath = "$dartCommonLib/httper",
            dartBaseObjectImport = "import '../../BaseObject.dart';",
            shareMainPath = "/share_generate_result/dto_vo",
    )
}


