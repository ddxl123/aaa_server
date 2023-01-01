package com.example.demo.share_generator.other_response_code_generator

import com.example.demo.controller.CodeMessage
import com.example.demo.controller.exception.ExceptionAdvice
import com.example.demo.share_generator.common.scanClasses
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.reflect.full.companionObject
import kotlin.reflect.full.memberProperties
import kotlin.reflect.full.staticFunctions
import kotlin.reflect.full.staticProperties
import kotlin.reflect.jvm.isAccessible
import kotlin.reflect.jvm.javaField

fun otherResponseCodeGenerator(dartDriftMainLib: String) {
    handle()
    Path("$dartDriftMainLib/httper").createDirectories()
    File("$dartDriftMainLib/httper/OtherResponseCode.dart").writeText(content())
}


var requiredContent = ""
var bodyContent = ""

fun handle() {
    ExceptionAdvice::class.companionObject!!.memberProperties.forEach {
        it.isAccessible = true
        val cm = it.javaField!!.get(null) as CodeMessage
        requiredContent += "\n        required Future<T> Function(String showMessage) code${cm.code},"
        bodyContent += """
        if (inputCode == ${cm.code}) {
            return await code${cm.code}("${cm.message}");
        }"""
    }
}

fun content(): String {
    return """
part of httper;
class OtherResponseCode {
    /// 返回是否被拦截处理。
    static Future<T?> handleCode<T>({
        required int inputCode,$requiredContent
    }) async {$bodyContent
        return null;
    }
}
"""
}