package com.example.demo.generator

import com.example.demo.code_message.CodeMessage
import java.io.File
import javax.persistence.Column
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.reflect.KClass
import kotlin.reflect.KMutableProperty1
import kotlin.reflect.jvm.javaField


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

enum class ClassNameType {
    Dto,
    Vo,
}

class ClassNameWrapper(private val nameClass: KClass<*>, val type: ClassNameType) {
    val name = nameClass.simpleName
    val nameWithType: String = nameClass.simpleName + type.name;
    val codeMessages: ArrayList<CodeMessage> = arrayListOf()
    val dtoFieldTargets: ArrayList<FieldTarget<*, *>> = arrayListOf()
    val voFieldTargets: ArrayList<FieldTarget<*, *>> = arrayListOf()

    init {
        var hasDto = false
        var hasVo = false
        for (declaredField in nameClass.java.declaredFields) {
            declaredField.isAccessible = true
            val cm = declaredField.get(null)
            if (cm is CodeMessage) {
                codeMessages.add(cm)
            }
            if (declaredField.name == ClassNameType.Dto.name.lowercase() + "s") {
                hasDto = true
                dtoFieldTargets.addAll((cm as ArrayList<*>).map { it as FieldTarget<*, *> })
            }
            if (declaredField.name == ClassNameType.Vo.name.lowercase() + "s") {
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

}

class PathClass(
        /**
         * 相对 [ShareObjectGenerator.dartGeneratorRootPath]+[ShareObjectGenerator.mainPath] 的路径。
         */
        val dartRelativePath: String,

        /**
         * 相对 [ShareObjectGenerator.kotlinGeneratorRootPath]+[ShareObjectGenerator.mainPath] 的路径。
         */
        val kotlinRelativePath: String,

        /**
         * 统一类名。
         */
        val classNameWrapper: ClassNameWrapper
) {
    val dartCompletePathNoClass: String = ShareObjectGenerator.dartGeneratorRootPath + ShareObjectGenerator.mainPath + dartRelativePath
    val kotlinCompletePath: String = ShareObjectGenerator.kotlinGeneratorRootPath + ShareObjectGenerator.mainPath + kotlinRelativePath
    private val dartCompletePathWithClassNoClass: String
    private val kotlinCompletePathWithClass: String
    val dartCompletePathWithClassAndSuffix: String
    val kotlinCompletePathWithClassSuffix: String

    init {
        if (dartCompletePathNoClass.contains("\\") || kotlinCompletePath.contains("\\")) {
            throw Exception("路径必须用/，不要使用 \\")
        }
        dartCompletePathWithClassNoClass = "$dartCompletePathNoClass/${classNameWrapper.nameWithType}"
        kotlinCompletePathWithClass = "$kotlinCompletePath/${classNameWrapper.nameWithType}"
        dartCompletePathWithClassAndSuffix = "$dartCompletePathWithClassNoClass.dart"
        kotlinCompletePathWithClassSuffix = "$kotlinCompletePathWithClass.kt"
    }
}

class ClassTarget(
        val pathClass: PathClass,
) {
    private val kotlinImports = mutableSetOf<String>()

    fun toKotlinContent(): String {
        return """
package ${ShareObjectGenerator.kotlinPackageName}.${(ShareObjectGenerator.mainPath + pathClass.kotlinRelativePath).split(Regex("/")).run { subList(1, count()) }.joinToString(".")}
${kotlinImports.joinToString("\n")}
data class ${pathClass.classNameWrapper.nameWithType}(
${
            fun(): String {
                val fts = when (pathClass.classNameWrapper.type) {
                    ClassNameType.Dto -> pathClass.classNameWrapper.dtoFieldTargets
                    ClassNameType.Vo -> pathClass.classNameWrapper.voFieldTargets
                    else -> throw Exception("未知 ClassNameType:${pathClass.classNameWrapper.type}")
                }
                var content = ""
                fts.forEach {
                    kotlinImports.add(it.kotlinImport)
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
            if (pathClass.classNameWrapper.type == ClassNameType.Dto) {
                "import '${pathClass.classNameWrapper.name + ClassNameType.Vo.name}.dart';"
            } else {
                ""
            }
        }
part '${pathClass.classNameWrapper.nameWithType}.g.dart';
@JsonSerializable()
class ${pathClass.classNameWrapper.nameWithType} extends BaseObject{
${
            fun(): String {
                val fts = when (pathClass.classNameWrapper.type) {
                    ClassNameType.Dto -> pathClass.classNameWrapper.dtoFieldTargets
                    ClassNameType.Vo -> pathClass.classNameWrapper.voFieldTargets
                    else -> throw Exception("未知 ClassNameType:${pathClass.classNameWrapper.type}")
                }
                var content = ""
                fts.forEach {
                    content += """
    /// ${it.explain}
    ${it.dartTypeName}${if (it.isForceNullable) "?" else ""} ${it.fieldName};
"""
                }
                return content
            }()
        }

${pathClass.classNameWrapper.nameWithType}({
${
            fun(): String {
                val fts = when (pathClass.classNameWrapper.type) {
                    ClassNameType.Dto -> pathClass.classNameWrapper.dtoFieldTargets
                    ClassNameType.Vo -> pathClass.classNameWrapper.voFieldTargets
                    else -> throw Exception("未知 ClassNameType:${pathClass.classNameWrapper.type}")
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
  factory ${pathClass.classNameWrapper.nameWithType}.fromJson(Map<String, dynamic> json) => _$${pathClass.classNameWrapper.nameWithType}FromJson(json);
    
  @override
  Map<String, dynamic> toJson() => _$${pathClass.classNameWrapper.nameWithType}ToJson(this);
  
  ${
            if (pathClass.classNameWrapper.type == ClassNameType.Dto) {
                """
          
  @JsonKey(ignore: true)
  int? code;

  @JsonKey(ignore: true)
  String message = "未分配 message！";

  @JsonKey(ignore: true)
  ${pathClass.classNameWrapper.name + ClassNameType.Vo.name}? vo;

  T handleCode<T>({
    // 前端 request 内部异常，统一只需消息。
    required T Function(String message) localExceptionMessage,
    
${
                    fun(): String {
                        var content = ""
                        pathClass.classNameWrapper.codeMessages.forEach {
                            content += if (it.isRequiredData) {
                                """
    // ${it.message}
    required T Function(String message, ${pathClass.classNameWrapper.name + ClassNameType.Vo.name} vo) code${it.code},
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
                        pathClass.classNameWrapper.codeMessages.forEach {
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

class ShareObjectGenerator {
    companion object {


        /**
         * com.xxx.xxx
         */
        var kotlinPackageName: String = ""

        /**
         * kotlin 项目根路径。
         */
        var kotlinGeneratorRootPath: String = ""

        /**
         * dart 项目根路径是。
         */
        var dartGeneratorRootPath: String = ""

        /**
         * dart 中每个 share_object 所继承的 BaseObject 的 import。
         *
         * 例如: import 'package:httper/BaseObject.dart';
         */
        var dartBaseObjectImport: String = ""

        /**
         * 相对根路径的主路径。
         */
        var mainPath: String = ""

        val targets = mutableListOf<ClassTarget>()

        fun setConfig(
                kotlinPackageName: String,
                kotlinGeneratorRootPath: String,
                dartGeneratorRootPath: String,
                dartBaseObjectImport: String,
                mainPath: String,
        ) {
            ShareObjectGenerator.kotlinPackageName = kotlinPackageName
            ShareObjectGenerator.kotlinGeneratorRootPath = kotlinGeneratorRootPath
            ShareObjectGenerator.dartGeneratorRootPath = dartGeneratorRootPath
            ShareObjectGenerator.dartBaseObjectImport = dartBaseObjectImport
            ShareObjectGenerator.mainPath = mainPath
        }

        fun run() {
            for (target in targets) {
                Path(target.pathClass.kotlinCompletePath).createDirectories()
                Path(target.pathClass.dartCompletePathNoClass).createDirectories()
                File(target.pathClass.kotlinCompletePathWithClassSuffix).writeText(target.toKotlinContent())
                File(target.pathClass.dartCompletePathWithClassAndSuffix).writeText(target.toDartContent())
            }
        }

    }
}
