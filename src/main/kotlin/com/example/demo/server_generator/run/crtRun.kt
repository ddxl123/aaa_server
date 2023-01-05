package com.example.demo.server_generator.run

import com.example.demo.entity.base.BaseIdAutoAssignable
import com.example.demo.entity.base.BaseIdManualAssignable
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.common.scanClasses
import java.io.File
import javax.persistence.Column
import javax.persistence.Entity
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.reflect.full.*
import kotlin.reflect.jvm.javaField

fun crtRun(kotlinPackageName: String, kotlinGeneratorRootPath: String) {
    find(kotlinPackageName = kotlinPackageName)
    Path("$kotlinGeneratorRootPath/server_generator/output").createDirectories()
    File("$kotlinGeneratorRootPath/server_generator/output/Crt.kt").writeText(content())
}

fun find(kotlinPackageName: String) {
    val cls = scanClasses(kotlinPackageName = kotlinPackageName)
    cls.forEach { cl ->
        val restAnno = cl.java.getAnnotation(Entity::class.java)
        if (restAnno != null) {
            val crtMembers = arrayListOf<CrtMember>()
            var idType: IdType? = null
            if (cl.isSubclassOf(BaseIdAutoAssignable::class)) {
                idType = IdType.idAutoAssignable
            } else if (cl.isSubclassOf(BaseIdManualAssignable::class)) {
                idType = IdType.idManualAssignalbe
            }
            if (idType != null) {
                packages.add("import ${cl.toString().removePrefix("class ")}")
                cl.memberProperties.forEach { m ->
                    val col = m.javaField!!.getAnnotation(Column::class.java)
                    val clientCol = m.javaField!!.getAnnotation(ClientColumn::class.java)
                    if (clientCol == null || !clientCol.isOnlyClient) {
                        val type = m.returnType.toString().removeSuffix("?")
                        crtMembers.add(CrtMember(name = m.name, isNullable = col.nullable, type = type))
                    }
                }
                tables.add(CrtTable(name = cl.simpleName!!, members = crtMembers, idType = idType))
            }
        }
    }
}

fun content(): String {
    var single = ""
    tables.forEach {
        var params = ""
        var assign = ""
        for (member in it.members) {
            if (member.name == "id") {
                when (it.idType) {
                    IdType.idAutoAssignable -> {
                        params += ""
                        assign += ""
                    }

                    IdType.idManualAssignalbe -> {
                        params += "                id: String,\n"
                        assign += "                    it.id = id\n"
                    }

                    else -> throw Exception("未处理：${it.idType}")
                }
            } else {
                params += "                ${member.name}: ${member.type}${if (member.isNullable) "?" else ""},\n"
                assign += "                    it.${member.name} = ${member.name}\n"
            }
        }
        single += """
            fun ${it.name.first().lowercase() + it.name.substring(1, it.name.length)}(
$params
            ): ${it.name} {
                return ${it.name}().also {
$assign
                }
            }
        """
    }
    return """
package com.example.demo.server_generator.output
${packages.joinToString("\n")}
class Crt {
    companion object {
$single
    }
}
"""
}

enum class IdType {
    idAutoAssignable,
    idManualAssignalbe,
}

class CrtMember(
        val name: String,
        val isNullable: Boolean,
        val type: String
)

class CrtTable(
        val name: String,
        val members: ArrayList<CrtMember>,
        val idType: IdType
)

val tables = arrayListOf<CrtTable>()
val packages = arrayListOf<String>()

