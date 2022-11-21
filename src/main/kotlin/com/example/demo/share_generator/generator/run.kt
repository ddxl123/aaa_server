package com.example.demo.share_generator.generator

fun main() {
    ShareObjectGenerator.run(
            kotlinPackageName = "com.example.demo",
            kotlinGeneratorRootPath = "D:/project/aaa_server/src/main/kotlin/com/example/demo",
            dartGeneratorRootPath = "D:/project/aaa/subpackages/httper/lib",
            dartBaseObjectImport = "import 'package:httper/BaseObject.dart';",
            mainPath = "/share_generator/share_object",
    )
}


