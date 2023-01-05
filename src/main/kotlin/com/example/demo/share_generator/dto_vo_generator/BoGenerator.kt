package com.example.demo.share_generator.dto_vo_generator

import com.example.demo.share_generator.common.getOrSetTypeTarget
import com.example.demo.share_generator.common.scanClasses
import com.example.demo.share_generator.dartDriftMainLib
import com.example.demo.share_generator.dto_vo_generator.annotation.Bo
import com.example.demo.share_generator.kotlinPackageName
import java.io.File
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.jvmErasure

fun boGenerator() {
    val cls = scanClasses(kotlinPackageName = kotlinPackageName)
    cls.forEach { cl ->
        val boAnno = cl.java.getAnnotation(Bo::class.java)
        if (boAnno != null) {
            val boField = arrayListOf<BoField>()
            cl.memberProperties.forEach { m ->
                boField.add(BoField(name = m.name, typeName = getOrSetTypeTarget(m.returnType.jvmErasure).dartType.typeName, isNullable = m.returnType.isMarkedNullable))
            }
            bos.add(BoTarget(className = cl.simpleName!!, boField))
        }
    }
    Path("$dartDriftMainLib/httper/share_generate_result/bo").createDirectories()
    File("$dartDriftMainLib/httper/share_generate_result/bo/bo.dart").writeText(dartContent())
}

val bos = arrayListOf<BoTarget>()

class BoTarget(
        val className: String,
        val fields: ArrayList<BoField>,
) {
    override fun toString(): String {
        return "(className: $className, fields: $fields)"
    }
}

class BoField(
        val name: String,
        val typeName: String,
        val isNullable: Boolean
) {
    override fun toString(): String {
        return "(name: $name, typeName: $typeName, isNullable: $isNullable)"
    }
}

fun dartContent(): String {
    var classContent = ""
    bos.forEach { it ->
        var fieldContent = ""
        var requiredContent = ""
        it.fields.forEach { f ->
            fieldContent += "    ${f.typeName}${if (f.isNullable) "?" else ""} ${f.name};\n"
            requiredContent += "    required this.${f.name},\n"
        }
        classContent += """
@JsonSerializable()
class ${it.className} {
$fieldContent
${it.className}({
$requiredContent
});

  factory ${it.className}.fromJson(Map<String, dynamic> json) => _$${it.className}FromJson(json);
  
  Map<String, dynamic> toJson() => _$${it.className}ToJson(this);
}
"""
    }
    return """
// ignore_for_file: non_constant_identifier_names
part of httper;
$classContent
"""
}