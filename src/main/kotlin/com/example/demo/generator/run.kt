package com.example.demo.generator

import com.example.demo.code_message.RegisterAndLoginWithUsername


fun main() {
    ShareObjectGenerator.setConfig(
            kotlinPackageName = "com.example.demo",
            kotlinGeneratorRootPath = "D:/project/aaa_server/src/main/kotlin/com/example/demo",
            dartGeneratorRootPath = "D:/project/aaa/subpackages/httper/lib",
            dartBaseObjectImport = "import 'package:httper/BaseObject.dart';",
            mainPath = "/share_object",
    )
    ShareObjectGenerator.targets.addAll(
            arrayListOf(
                    ClassTarget(
                            PathClass(
                                    "",
                                    "",
                                    ClassNameWrapper(RegisterAndLoginWithUsername::class, ClassNameType.Dto)
                            ),
                    ),
                    ClassTarget(
                            PathClass(
                                    "",
                                    "",
                                    ClassNameWrapper(RegisterAndLoginWithUsername::class, ClassNameType.Vo)
                            ),
                    )
            )
    )
    ShareObjectGenerator.run()
}



