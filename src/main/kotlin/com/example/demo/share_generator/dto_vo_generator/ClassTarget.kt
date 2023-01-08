package com.example.demo.share_generator.dto_vo_generator

import com.example.demo.controller.CodeMessage
import kotlin.reflect.jvm.kotlinProperty

enum class TargetClassType {
    Dto,
    Vo,
}

class ClassTarget(
        /**
         * 相对 [DtoVoGenerator.dartGeneratorRootCompletePath]+[DtoVoGenerator.shareMainPath] 的路径。
         */
        val dartRelativePath: String,

        /**
         * 相对 [DtoVoGenerator.kotlinGeneratorRootPath]+[DtoVoGenerator.shareMainPath] 的路径。
         */
        val kotlinRelativePath: String,

        /**
         * 将要生成的类。
         */
        val targetClass: Class<*>,

        /**
         * 将要生成的类类型。
         */
        val targetClassType: TargetClassType
) {


    val dartCompletePathNoClass: String = DtoVoGenerator.dartGeneratorRootCompletePath + DtoVoGenerator.shareMainPath + dartRelativePath
    val kotlinCompletePath: String = DtoVoGenerator.kotlinGeneratorRootPath + DtoVoGenerator.shareMainPath + kotlinRelativePath
    val dartCompletePathWithClassNoClass: String
    val kotlinCompletePathWithClass: String
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
            if (declaredField.kotlinProperty == null) {
                // 如果这里抛出异常，则可能 companion.object 中不存在 codeMessage。
                val cm = declaredField.get(null)
                if (cm is CodeMessage) {
                    codeMessages.add(cm)
                }
            } else {
                val cm = declaredField.get(targetClass.getDeclaredConstructor().newInstance())
                if (declaredField.name == TargetClassType.Dto.name.lowercase() + "s") {
                    hasDto = true
                    dtoFieldTargets.addAll((cm as ArrayList<*>).map { it as FieldTarget<*, *> })
                }
                if (declaredField.name == TargetClassType.Vo.name.lowercase() + "s") {
                    hasVo = true
                    voFieldTargets.addAll((cm as ArrayList<*>).map { it as FieldTarget<*, *> })
                }
            }
        }
        if (!hasDto) {
            throw Exception("缺少名为 dtos 的字段\n${targetClass.kotlin.qualifiedName}\n" +
                    "尽可能让一对 Dto-Vo 充分发挥作用，不能让其过于单一，否则将会日积月累的叠加大量的简单 Dto-Vo对。")
        }
        if (!hasVo) {
            throw Exception("缺少名为 vos 的字段\n${targetClass.kotlin.qualifiedName}\n" +
                    "该 vos 字段是必需的，因为 Dart 中，如果字段由不存在变为存在，那么 Dart 对 XXX.fromJson 的引用需要手动由 null 改为 XXX.fromJson，" +
                    "vos 字段是必需的，这将让 Dart 端对 XXX.fromJson 的引用无需作出改变，降低维护的繁琐性。")
        }
        if (targetClassType == TargetClassType.Dto) {
            val all = arrayListOf(Int::class, Float::class, Long::class, Boolean::class, String::class)
            val result = dtoFieldTargets.map { it.typeTarget.kClass.qualifiedName }.joinToString("").contains(Regex(all.joinToString("|") { "(${it.qualifiedName})" }))

            if (!result) {
                dtoFieldTargets.add(FieldTarget<Any, Any>("dto_padding", Boolean::class, isForceNullable = true, explain = "填充字段"))
                println("--- 已自动添加填充字段：dto_padding\n" +
                        "|   dtos 数组必需至少包含一个基本数据类型的元素！\n" +
                        "|   否则 controller 在接收 Dto 对象时，会报 InvalidDefinitionException 异常！\n" +
                        "--- ${targetClass.name}.dtos"
                )
            }
        }
        if (targetClassType == TargetClassType.Vo) {
            if (voFieldTargets.isEmpty()) {
                voFieldTargets.add(FieldTarget<Any, Any>("vo_padding", Boolean::class, isForceNullable = true, explain = "填充字段"))
                println("--- 已自动添加填充字段：vo_padding\n" +
                        "|   vos 数组必需至少包含一个元素！\n" +
                        "--- ${targetClass.name}.vos"
                )
            }
        }
    }


    fun toKotlinContent(): String {
        return """
package ${DtoVoGenerator.kotlinPackageName}.${(DtoVoGenerator.shareMainPath + kotlinRelativePath).split(Regex("/")).run { subList(1, count()) }.joinToString(".")}
/**
 * [${targetClass.name}]
 */
class ${classNameWithType}(
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
"""
    }

    fun toDartContent(): String {
        return """
// ignore_for_file: non_constant_identifier_names
part of httper;
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
  HttperException? httperException;
  
  @JsonKey(ignore: true)
  StackTrace? st;

  @JsonKey(ignore: true)
  ${className + TargetClassType.Vo.name}? vo;

  /// 内部抛出的异常将在 [otherException] 中捕获。
  Future<T> handleCode<T>({
    // code 为 null 时的异常（request 函数内部捕获到的异常）
    required Future<T> Function(int? code, HttperException httperException, StackTrace st) otherException,
${
                    fun(): String {
                        var content = ""
                        codeMessages.forEach {
                            content += if (it.isRequiredData) {
                                """
    // message: ${it.message}
    // explain: ${it.explain}
    required Future<T> Function(String showMessage, ${className + TargetClassType.Vo.name} vo) code${it.code},
    """
                            } else {
                                """
    // message: ${it.message}
    // explain: ${it.explain}
    required Future<T> Function(String showMessage) code${it.code},
    """
                            }
                        }
                        return content
                    }()
                }
    }) async {
    try {
${
                    fun(): String {
                        var content = ""
                        codeMessages.forEach {
                            content += if (it.isRequiredData) {
                                """
        if (code == ${it.code}) return await code${it.code}(httperException!.showMessage, vo!);
"""
                            } else {
                                """
        if (code == ${it.code}) return await code${it.code}(httperException!.showMessage);
"""
                            }
                        }
                        return content
                    }()
                }
    } catch (e, st) {
      if (e is HttperException) {
        return await otherException(code, e, st);
      }
      return await otherException(code, HttperException(showMessage: '请求异常！', debugMessage: e.toString()), st);
    }
    if (code != null) {
      return await otherException(code, HttperException(showMessage: '请求异常！', debugMessage: '响应码 ${"\$code"} 未处理！'), st!);
    }
    return await otherException(code, httperException!, st!);
  }"""
            } else ""
        }
}
"""
    }
}