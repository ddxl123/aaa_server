package com.example.demo.share_generator.client_table_generator

import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import com.example.demo.share_generator.common.scanClasses
import com.example.demo.share_generator.common.getTypeTarget
import org.springframework.util.ClassUtils
import java.io.File
import javax.persistence.Column
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
         * 相对 [dartLibPath] 的生成根路径。
         *
         * 例如：/table/cloud_table
         */
        private lateinit var dartRelativeGenerateRootPath: String

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

        private val clientTableTargets = arrayListOf<ClientTableTarget>()


        fun run(
                kotlinRelativeScanPath: String,
                kotlinPackageName: String,
                dartRelativeGenerateRootPath: String,
                dartLibPath: String,
                dartShareEnumImport: String,
        ) {
            Companion.kotlinRelativeScanPath = kotlinRelativeScanPath
            Companion.kotlinPackageName = kotlinPackageName
            Companion.dartRelativeGenerateRootPath = dartRelativeGenerateRootPath
            Companion.dartLibPath = dartLibPath
            Companion.dartShareEnumImport = dartShareEnumImport

            handleClassTarget()
            for (clientTableTarget in clientTableTargets) {
                Path(dartLibPath + "/" + dartRelativeGenerateRootPath + clientTableTarget.path).createDirectories()
                File(dartLibPath + "/" + dartRelativeGenerateRootPath + clientTableTarget.path + "/" + clientTableTarget.tableName + ".dart").writeText(clientTableTarget.toDartSingleTableContent())
            }
            Path(dartLibPath).createDirectories()
            File("$dartLibPath/DriftDb.dart").writeText(driftDbTablePartContent())
        }

        private fun driftDbTablePartContent(): String {
            return """
library drift_db;

import 'dart:async';
import 'dart:io';

import 'package:drift/drift.dart';
import 'package:drift/native.dart';
import 'package:drift_custom/ReferenceTo.dart';
import 'package:path/path.dart';
import 'package:path_provider/path_provider.dart';
import 'package:tools/tools.dart';
$dartShareEnumImport

part 'dao/query/GeneralQueryDAO.dart';

part 'dao/InsertDAO.dart';

part 'dao/UpdateDAO.dart';

part 'dao/DeleteDAO.dart';

part 'table/Base.dart';

part 'table/local/Local.dart';

part 'custom/sync_tag.dart';

part 'custom/sync_curd.dart';

part 'table/local/Sync.dart';

part 'drift_db_init.dart';

part 'DriftDb.drift.dart';

part 'DriftDb.ref.dart';

part 'DriftDb.ctr.dart';

part 'DriftDb.reset.dart';

${
                fun(): String {
                    var content = ""
                    for (clientTableTarget in clientTableTargets) {
                        content += """
part '${dartRelativeGenerateRootPath.removePrefix("/")}${clientTableTarget.path}${
                            if (clientTableTarget.path.trim() == "/" || clientTableTarget.path.isBlank()) "" else "/"
                        }${clientTableTarget.tableName}.dart';
            """
                    }
                    return content
                }()
            }
const List<Type> cloudTableClasses = [
${
                fun(): String {
                    var content = ""
                    for (clientTableTarget in clientTableTargets) {
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

        private fun handleClassTarget() {
            scanClasses(kotlinPackageName).forEach { kClass ->
                // /xxx 转 .xxx
                val toClassName = ClassUtils.convertResourcePathToClassName(kotlinRelativeScanPath)
                if (kClass.qualifiedName != null && kClass.qualifiedName!!.contains(toClassName)) {
                    // clas.annotations 只会获取到 kotlin 的注解，clas.java.annotations 只会获取到 java 的注解。
                    val tableClassAnnotation = kClass.java.getAnnotation(ClientTable::class.java)
                    if (tableClassAnnotation != null) {
                        val path = kClass.qualifiedName!!.split("$kotlinPackageName$toClassName.").last().split(".")
                                .toMutableList().run {
                                    removeLast()
                                    "/" + this.joinToString("/")
                                }
                        val clientMemberTargets = arrayListOf<ClientMemberTarget>()
                        kClass.memberProperties.forEach { memberProperty ->
                            // TODO: 有时候第一种能获取到全部注解，有时候第二种能获取到全部注解，未知原因。
                            //  1. println(memberProperty.annotations)
                            //  2. println(memberProperty.javaField!!.annotations)
                            val clientColumnAnnotation = memberProperty.javaField!!.getAnnotation(ClientColumn::class.java)
                            if (clientColumnAnnotation != null) {
                                val cmt = ClientMemberTarget(
                                        name = memberProperty.name,
                                        // 需要的是 kotlin 的类型(如 Int)，并非 java 类型(如 Integer/int)，
                                        typeTarget = getTypeTarget(memberProperty.javaField!!.type.kotlin),
//                                        typeTarget = getTypeTarget(
//                                                simpleTypeName = memberProperty.returnType.toString().split(".").last().replace("?", "")
//                                        ),
                                        // clas.annotations 只会获取到 kotlin 的注解，clas.java.annotations 只会获取到 java 的注解。
                                        isNullable = memberProperty.javaField!!.getDeclaredAnnotationsByType(Column::class.java).firstOrNull()?.nullable
                                                ?: true,
                                        referenceTos = clientColumnAnnotation.referenceTo.map { ref -> ref.simpleName!! } as ArrayList<String>,
                                )
                                clientMemberTargets.add(cmt)
                            }
                        }
                        clientTableTargets.add(
                                ClientTableTarget(
                                        path = path,
                                        tableName = kClass.simpleName!!,
                                        clientMemberTargets = clientMemberTargets
                                )
                        )

                    }
                }
            }
        }
    }

}

