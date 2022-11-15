package com.example.demo.dto_vo.generator

import java.io.File
import javax.persistence.Column
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.jvm.javaField

enum class DtoOrVo {
    dto,
    vo,
}


class FieldTarget<out T, out V> {

    /**
     * [fieldTargetObj] XXX:name
     *
     * [isForceNullable] 是否强制为空或不为空，无论 XXX:name 的注解是否为空。
     * 当 [isForceNullable] 为 null 时，会根据 XXX:name 自动检测是否为空。
     *
     * [explain] 当前变量的注释。
     */
    constructor(
            fieldTargetObj: KMutableProperty1<T, V>,
            isForceNullable: Boolean? = null,
            explain: String = ""
    ) {
        this.kotlinTypeName = fieldTargetObj.returnType.toString().split(".").last().replace("?", "")
        val targetValue: TargetValue = typeMap[this.kotlinTypeName]
                ?: throw Exception("未找到对应的 dartType！need:${this.kotlinTypeName}")
        this.fieldName = fieldTargetObj.name.replace(Regex("[A-Z]")) { matchResult -> "_${matchResult.value.lowercase()}" }
        this.dartTypeName = targetValue.dartTypeName
        this.kotlinImport = targetValue.kotlinImport
        this.dartImport = targetValue.dartImport
        if (isForceNullable != null) {
            this.isForceNullable = isForceNullable
        } else {
            // 识别注解，解析出是否为空
            this.isForceNullable = fieldTargetObj.javaField!!.getDeclaredAnnotationsByType(Column::class.java).firstOrNull()?.nullable
                    ?: true
        }
        this.explain = explain
    }

    /**
     * [fieldName] 自命名的字段名。
     *
     * [kotlinType] 需要的 kotlin 的类型。
     * 可查看映射：[typeMap]
     *
     * [isForceNullable] 是否强制为空或不为空。
     *
     * [explain] 当前变量的注释。
     */
    constructor(
            fieldName: String,
            kotlinType: KClass<*>,
            isForceNullable: Boolean,
            explain: String = ""
    ) {
        this.kotlinTypeName = kotlinType.simpleName!!
        val targetValue: TargetValue = typeMap[this.kotlinTypeName]
                ?: throw Exception("未找到对应的 dartType！need:${this.kotlinTypeName}")
        this.fieldName = fieldName
        this.dartTypeName = targetValue.dartTypeName
        this.kotlinImport = targetValue.kotlinImport
        this.dartImport = targetValue.dartImport
        this.isForceNullable = isForceNullable
        this.explain = explain
    }


    val fieldName: String
    val kotlinTypeName: String
    val dartTypeName: String
    val kotlinImport: String
    val dartImport: String
    val isForceNullable: Boolean
    val explain: String
}

class ClassTarget<out T, out V>(
        val className: String,
        private vararg val fieldTargets: FieldTarget<T, V>
) {
    private val kotlinImports = mutableSetOf<String>()

    fun toKotlinContent(dtoOrVo: DtoOrVo): String {
        var fieldTargetContent = ""
        for (fieldTarget in fieldTargets) {
            kotlinImports.add(fieldTarget.kotlinImport)
            fieldTargetContent += """
    // ${fieldTarget.explain}
    var ${fieldTarget.fieldName}: ${fieldTarget.kotlinTypeName}${if (fieldTarget.isForceNullable) '?' else ""},${"\n"}"""
        }

        return """
package com.example.demo.dto_vo.${dtoOrVo.name}
${kotlinImports.joinToString("\n")}
data class $className(
$fieldTargetContent
)
""".trimIndent()
    }

    fun toDartContent(): String {
        var fieldTargetContent = ""
        var requiredContent = ""
        for (fieldTarget in fieldTargets) {
            fieldTargetContent += """
    /// ${fieldTarget.explain}
    ${fieldTarget.dartTypeName}${if (fieldTarget.isForceNullable) "?" else ""} ${fieldTarget.fieldName};
"""
            requiredContent += """
    required this.${fieldTarget.fieldName},
"""
        }
        return """
// ignore_for_file: non_constant_identifier_names
import 'package:json_annotation/json_annotation.dart';
part '${className}.g.dart';
@JsonSerializable()
class $className {
$fieldTargetContent
$className({
$requiredContent
});
    factory $className.fromJson(Map<String, dynamic> json) => _$${className}FromJson(json);
    Map<String, dynamic> toJson() => _$${className}ToJson(this);
}
"""
    }
}

class DtoVoGenerator {
    private val kotlinMainPath = "D:\\project\\aaa_server\\src\\main\\kotlin\\com\\example\\demo\\dto_vo"

    private val dartMainPath = "D:\\project\\aaa\\subpackages\\httper\\lib\\dto_vo"

    fun run() {
        write(dtos, DtoOrVo.dto)
        write(bos, DtoOrVo.vo)
    }


    /**
     * [list] 集合。
     *
     * [dtoOrVo] dto 或 do 字符。
     */
    private fun <T, V> write(list: Array<out ClassTarget<T, V>>, dtoOrVo: DtoOrVo) {
        for (dto in list) {
            val kotlinFile = File("$kotlinMainPath\\${dtoOrVo.name}\\${dto.className}.kt")
            kotlinFile.writeText(dto.toKotlinContent(dtoOrVo))

            val dartFile = File("$dartMainPath\\${dtoOrVo.name}\\${dto.className}.dart")
            dartFile.writeText(dto.toDartContent())
        }
    }

}
