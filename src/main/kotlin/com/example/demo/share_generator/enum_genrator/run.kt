package com.example.demo.share_generator.enum_genrator

import com.example.demo.share_generator.common.typeSet
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.createDirectories

fun enumGeneratorRun(dartCommonLib: String) {
    Path("$dartCommonLib/share_common").createDirectories()
    File("$dartCommonLib/share_common/share_enum.dart").writeText(enumContent())
}

fun enumContent(): String {
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
    return content
}