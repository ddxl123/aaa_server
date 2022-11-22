package com.example.demo.share_generator

import com.example.demo.share_generator.client_table_generator.clientTableGeneratorRun
import com.example.demo.share_generator.common.typeSet
import com.example.demo.share_generator.dto_vo_generator.dtoVoGeneratorRun
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.createDirectories

val kotlinPackageName = "com.example.demo"
val dartCommonLib = "D:/project/aaa/subpackages/drift_main/lib"
fun main() {
    clientTableGeneratorRun(kotlinPackageName = kotlinPackageName, dartCommonLib = dartCommonLib)
    dtoVoGeneratorRun(kotlinPackageName = kotlinPackageName, dartCommonLib = dartCommonLib)
    g()
}

fun g() {
    var content = ""
    for (typeTarget in typeSet) {
        if (typeTarget.kClass.java.isEnum) {

            content += """
enum ${typeTarget.kClass.simpleName} {
  ${
                fun(): String {
                    var c = ""
                    for (enumConstant in typeTarget.kClass.java.enumConstants) {
                        c += """
  ${enumConstant},
"""
                    }

                    return c
                }()
            }
}
        """
        }
    }
    Path("$dartCommonLib/share_common").createDirectories()
    File("$dartCommonLib/share_common/share_enum.dart").writeText(content)
}