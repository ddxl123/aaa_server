package com.example.demo.share_generator.generator

import com.example.demo.share_generator.CodeMessage

enum class TargetClassType {
    Dto,
    Vo,
}

class ClassTarget(
        /**
         * 相对 [ShareObjectGenerator.dartGeneratorRootPath]+[ShareObjectGenerator.mainPath] 的路径。
         */
        private val dartRelativePath: String,

        /**
         * 相对 [ShareObjectGenerator.kotlinGeneratorRootPath]+[ShareObjectGenerator.mainPath] 的路径。
         */
        private val kotlinRelativePath: String,

        /**
         * 将要生成的类。
         */
        targetClass: Class<*>,

        /**
         * 将要生成的类类型。
         */
        private val targetClassType: TargetClassType
) {


    val dartCompletePathNoClass: String = ShareObjectGenerator.dartGeneratorRootPath + ShareObjectGenerator.mainPath + dartRelativePath
    val kotlinCompletePath: String = ShareObjectGenerator.kotlinGeneratorRootPath + ShareObjectGenerator.mainPath + kotlinRelativePath
    private val dartCompletePathWithClassNoClass: String
    private val kotlinCompletePathWithClass: String
    val dartCompletePathWithClassAndSuffix: String
    val kotlinCompletePathWithClassSuffix: String


    private val className: String = targetClass.simpleName
    private val classNameWithType: String = targetClass.simpleName + targetClassType.name;
    private val codeMessages: ArrayList<CodeMessage> = arrayListOf()
    private val dtoFieldTargets: ArrayList<FieldTarget<*, *>> = arrayListOf()
    private val voFieldTargets: ArrayList<FieldTarget<*, *>> = arrayListOf()


    init {
        if (dartCompletePathNoClass.contains("\\") || kotlinCompletePath.contains("\\")) {
            throw Exception("路径必须用/，不要使用 \\")
        }
        dartCompletePathWithClassNoClass = "$dartCompletePathNoClass/${classNameWithType}"
        kotlinCompletePathWithClass = "$kotlinCompletePath/${classNameWithType}"
        dartCompletePathWithClassAndSuffix = "$dartCompletePathWithClassNoClass.dart"
        kotlinCompletePathWithClassSuffix = "$kotlinCompletePathWithClass.kt"


        var hasDto = false
        var hasVo = false
        for (declaredField in targetClass.declaredFields) {
            declaredField.isAccessible = true
            val cm = declaredField.get(null)
            if (cm is CodeMessage) {
                codeMessages.add(cm)
            }
            if (declaredField.name == TargetClassType.Dto.name.lowercase() + "s") {
                hasDto = true
                dtoFieldTargets.addAll((cm as ArrayList<*>).map { it as FieldTarget<*, *> })
            }
            if (declaredField.name == TargetClassType.Vo.name.lowercase() + "s") {
                hasVo = true
                voFieldTargets.addAll((cm as ArrayList<*>).map { it as FieldTarget<*, *> })
            }
        }
        if (!hasDto) {
            throw Exception("缺少名为 dtos 的字段")
        }
        if (!hasVo) {
            throw Exception("缺少名为 vos 的字段")
        }
    }


    fun toKotlinContent(): String {
        return """
package ${ShareObjectGenerator.kotlinPackageName}.${(ShareObjectGenerator.mainPath + kotlinRelativePath).split(Regex("/")).run { subList(1, count()) }.joinToString(".")}
${
            fun(): String {
                val fts = when (targetClassType) {
                    TargetClassType.Dto -> dtoFieldTargets
                    TargetClassType.Vo -> voFieldTargets
                    else -> throw Exception("未知 ClassNameType:${targetClassType}")
                }
                return (fts.map { it.typeTarget.kotlinTypeImport }).joinToString("\n")
            }()
        }
data class ${classNameWithType}(
${
            fun(): String {
                val fts = when (targetClassType) {
                    TargetClassType.Dto -> dtoFieldTargets
                    TargetClassType.Vo -> voFieldTargets
                    else -> throw Exception("未知 ClassNameType:${targetClassType}")
                }
                var content = ""
                fts.forEach {
                    content += """
    // ${it.explain}
    var ${it.fieldName}: ${it.kotlinTypeName}${if (it.isForceNullable) '?' else ""},${"\n"}"""
                }
                return content
            }()
        }
)
""".trimIndent()
    }

    fun toDartContent(): String {
        return """
// ignore_for_file: non_constant_identifier_names
${ShareObjectGenerator.dartBaseObjectImport}
import 'package:json_annotation/json_annotation.dart';
${
            if (targetClassType == TargetClassType.Dto) {
                "import '${className + TargetClassType.Vo.name}.dart';"
            } else {
                ""
            }
        }
part '${classNameWithType}.g.dart';
@JsonSerializable()
class $classNameWithType extends BaseObject{
${
            fun(): String {
                val fts = when (targetClassType) {
                    TargetClassType.Dto -> dtoFieldTargets
                    TargetClassType.Vo -> voFieldTargets
                    else -> throw Exception("未知 ClassNameType:${targetClassType}")
                }
                var content = ""
                fts.forEach {
                    content += """
    /// ${it.explain}
    ${it.typeTarget.dartType.typeName}${if (it.isForceNullable) "?" else ""} ${it.fieldName};
"""
                }
                return content
            }()
        }

${classNameWithType}({
${
            fun(): String {
                val fts = when (targetClassType) {
                    TargetClassType.Dto -> dtoFieldTargets
                    TargetClassType.Vo -> voFieldTargets
                    else -> throw Exception("未知 ClassNameType:${targetClassType}")
                }
                var content = ""
                fts.forEach {
                    content += """
    required this.${it.fieldName},
"""
                }
                return content
            }()
        }
});
  factory ${classNameWithType}.fromJson(Map<String, dynamic> json) => _$${classNameWithType}FromJson(json);
    
  @override
  Map<String, dynamic> toJson() => _$${classNameWithType}ToJson(this);
  
  ${
            if (targetClassType == TargetClassType.Dto) {
                """
          
  @JsonKey(ignore: true)
  int? code;

  @JsonKey(ignore: true)
  String message = "未分配 message！";

  @JsonKey(ignore: true)
  ${className + TargetClassType.Vo.name}? vo;

  T handleCode<T>({
    // 前端 request 内部异常，统一只需消息。
    required T Function(String message) localExceptionMessage,
    
${
                    fun(): String {
                        var content = ""
                        codeMessages.forEach {
                            content += if (it.isRequiredData) {
                                """
    // ${it.message}
    required T Function(String message, ${className + TargetClassType.Vo.name} vo) code${it.code},
    """
                            } else {
                                """
    // ${it.message}
    required T Function(String message) code${it.code},
    """
                            }
                        }
                        return content
                    }()
                }
                
    }) {
    
${
                    fun(): String {
                        var content = ""
                        codeMessages.forEach {
                            content += if (it.isRequiredData) {
                                """
    if (code == ${it.code}) {
        return code${it.code}(message, vo!);
    }
"""
                            } else {
                                """
    if (code == ${it.code}) {
        return code${it.code}(message);
    }
"""
                            }
                        }
                        return content
                    }()
                }
                
    throw "未处理 code:${"\$code"}";
  }
      """
            } else ""
        }
}
"""
    }
}