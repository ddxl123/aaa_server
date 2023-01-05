package com.example.demo.share_generator.client_table_generator

import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import com.example.demo.share_generator.common.scanClasses
import com.example.demo.share_generator.common.getTypeTarget
import org.springframework.util.ClassUtils
import java.io.File
import javax.persistence.Column
import javax.persistence.Entity
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.reflect.full.*
import kotlin.reflect.jvm.*


class ClientTableGenerator {
    companion object {
        /**
         * kotlin 包内的相对路径。
         */
        lateinit var kotlinRelativeScanPath: String

        /**
         * kotlin 包名。
         */
        private lateinit var kotlinPackageName: String

        /**
         * 相对 [dartLibPath] 的 cloudTable 生成根路径。
         *
         * 例如：/table/cloud_table
         */
        private lateinit var dartCloudTableRelativeGenerateRootPath: String

        /**
         * dart 中 share_enum 的 import。
         *
         * 例如：import 'package:drift_main/share_common/share_enum.dart';
         */
        private lateinit var dartShareEnumImport: String

        /**
         * dart 中的 DriftDb.dart 的生成路径。
         */
        private lateinit var dartLibPath: String

        private val dartTableTargets = arrayListOf<DartTableTarget>()


        fun run(
                kotlinRelativeScanPath: String,
                kotlinPackageName: String,
                dartCloudTableRelativeGenerateRootPath: String,
                dartLibPath: String,
                dartShareEnumImport: String,
        ) {
            Companion.kotlinRelativeScanPath = kotlinRelativeScanPath
            Companion.kotlinPackageName = kotlinPackageName
            Companion.dartCloudTableRelativeGenerateRootPath = dartCloudTableRelativeGenerateRootPath
            Companion.dartLibPath = dartLibPath
            Companion.dartShareEnumImport = dartShareEnumImport

            handleDartCloudTableClassTarget()
            for (dartCloudTableTarget in dartTableTargets) {
                Path(dartLibPath + "/" + dartCloudTableRelativeGenerateRootPath + dartCloudTableTarget.kotlinPath).createDirectories()
                File(dartLibPath + "/" + dartCloudTableRelativeGenerateRootPath + dartCloudTableTarget.kotlinPath + "/" + dartCloudTableTarget.tableName + ".dart").writeText(dartCloudTableTarget.toDartSingleTableContent())
            }
            Path(dartLibPath).createDirectories()
            File("$dartLibPath/DriftDb.dart").writeText(driftDbTablePartContent())
        }

        private fun driftDbTablePartContent(): String {
            return """
library drift_db;

import 'dart:async';
import 'dart:io';
import 'dart:math';

import 'package:drift/drift.dart';
import 'package:drift/native.dart';
import 'package:drift_custom/ReferenceTo.dart';
import 'package:path/path.dart';
import 'package:path_provider/path_provider.dart';
import 'package:tools/tools.dart';
$dartShareEnumImport

part 'dao/query/GeneralQueryDAO.dart';

part 'dao/insert/InsertDAO.dart';

part 'dao/insert/RawInsertDAO.dart';

part 'dao/UpdateDAO.dart';

part 'dao/DeleteDAO.dart';

part 'table/Base.dart';

part 'custom/sync_tag.dart';

part 'custom/sync_curd.dart';

part 'drift_db_init.dart';

part 'DriftDb.drift.dart';

part 'DriftDb.ref.dart';

part 'DriftDb.crt.dart';

part 'DriftDb.reset.dart';

${
                fun(): String {
                    var content = ""
                    for (dartCloudTableTarget in dartTableTargets) {
                        content += """
part '${dartCloudTableRelativeGenerateRootPath.removePrefix("/")}${dartCloudTableTarget.kotlinPath}${
                            if (dartCloudTableTarget.kotlinPath.trim() == "/" || dartCloudTableTarget.kotlinPath.isBlank()) "" else "/"
                        }${dartCloudTableTarget.tableName}.dart';
"""
                    }
                    return content
                }()
            }
const List<Type> tableClasses = [
${
                fun(): String {
                    var content = ""
                    for (clientTableTarget in dartTableTargets) {
                        content += """
  ${clientTableTarget.tableName},
"""
                    }
                    return content
                }()
            }
];
        """
        }

        private fun handleDartCloudTableClassTarget() {
            scanClasses(kotlinPackageName).forEach { kClass ->
                // /xxx 转 .xxx
                val toClassName = ClassUtils.convertResourcePathToClassName(kotlinRelativeScanPath)
                if (kClass.qualifiedName != null && kClass.qualifiedName!!.contains(toClassName)) {
                    // clas.annotations 只会获取到 kotlin 的注解，clas.java.annotations 只会获取到 java 的注解。
                    val clientTableClassAnnotation = kClass.java.getAnnotation(ClientTable::class.java)
                    val kotlinTableClassAnnotation = kClass.java.getAnnotation(Entity::class.java)
                    if (clientTableClassAnnotation != null) {
                        val kotlinPath = kClass.qualifiedName!!.split("$kotlinPackageName$toClassName.").last().split(".")
                                .toMutableList().run {
                                    removeLast()
                                    "/" + this.joinToString("/")
                                }
                        val dartMemberTargets = arrayListOf<DartMemberTarget>()
                        kClass.memberProperties.forEach { memberProperty ->
                            val clientColumnAnnotation = memberProperty.javaField!!.getAnnotation(ClientColumn::class.java)
                            if (clientColumnAnnotation != null) {
                                if (clientColumnAnnotation.isOnlyClient && !memberProperty.name.startsWith("client_")) {
                                    throw Exception("${kClass.simpleName}.${memberProperty.name} 未添加 client_ 前缀，因为它是 client 类型")
                                }
                                val cmt = DartMemberTarget(
                                        name = memberProperty.name,
                                        typeTarget = getTypeTarget(memberProperty.javaField!!.type.kotlin),
                                        isNullable = memberProperty.javaField!!.getDeclaredAnnotationsByType(Column::class.java).firstOrNull()?.nullable
                                                ?: true,
                                        referenceTos = clientColumnAnnotation.referenceTo.map { ref -> ref.simpleName!! } as ArrayList<String>,
                                )
                                dartMemberTargets.add(cmt)
                            }
                        }
                        dartTableTargets.add(
                                DartTableTarget(
                                        kotlinPath = kotlinPath,
                                        tableName = kClass.simpleName!!,
                                        tableType = if (kotlinTableClassAnnotation == null) TableType.client else TableType.cloud,
                                        dartMemberTargets = dartMemberTargets
                                )
                        )

                    }
                }
            }
        }
    }

}

