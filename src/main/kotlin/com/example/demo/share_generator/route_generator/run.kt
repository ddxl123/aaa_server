package com.example.demo.share_generator.route_generator

import com.example.demo.share_generator.common.scanClasses
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.reflect.full.findAnnotation
import kotlin.reflect.full.findAnnotations
import kotlin.reflect.full.functions

fun routeGenerator(dartCommonLib: String, kotlinPackageName: String) {
    var port: Int? = null
    var address: String? = null
    for (readLine in File("D:\\project\\aaa_server\\src\\main\\resources\\application.yml").readLines()) {
        val portStr = "  port: ";
        if (readLine.startsWith(portStr)) {
            port = readLine.substring(portStr.length, readLine.length).toInt()
        }
        val addressStr = "  address: "
        if (readLine.startsWith(addressStr)) {
            address = readLine.substring(addressStr.length, readLine.length)
        }
    }
    if (port == null || address == null) {
        throw Exception("port 和 address不能为空，port: $port address: $address")
    }

    val list = find(kotlinPackageName);

    Path("$dartCommonLib/httper").createDirectories()
    File("$dartCommonLib/httper/HttpPath.dart").writeText(routeContent(port, address, list))
}

fun find(kotlinPackageName: String): ArrayList<String> {
    val list = arrayListOf<String>()
    val clas = scanClasses(kotlinPackageName)
    clas.forEach {
        val restAnno = it.java.getAnnotation(RestController::class.java)
        if (restAnno != null) {
            val reqMAnno = it.java.getAnnotation(RequestMapping::class.java)!!
            for (function in it.functions) {
                val getM = function.findAnnotation<GetMapping>()
                val postM = function.findAnnotation<PostMapping>()
                if (getM != null) {
                    list.add(reqMAnno.value.first() + getM.value.first())
                }
                if (postM != null) {
                    list.add(reqMAnno.value.first() + postM.value.first())
                }
            }
        }
    }
    return list
}

fun routeContent(port: Int, address: String, list: ArrayList<String>): String {
    return """
part of httper;

/// 该文件由服务端生成。

class HttpPath {
  /// 基础 path
  static const String BASE_PATH_LOCAL = 'http://$address:$port';
  
${
        fun(): String {
            var content = ""
            list.forEach {
                val itContent = it.replace("/", "_").removePrefix("_")
                content += "  static const String ${itContent.uppercase()} = '$it';\n\n"
            }
            return content
        }()
    }}

""";
}