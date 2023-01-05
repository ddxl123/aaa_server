package com.example.demo.server_generator.run

import com.example.demo.share_generator.common.scanClasses
import com.example.demo.share_generator.kotlinGeneratorRootPath
import com.example.demo.share_generator.kotlinPackageName
import java.io.File
import javax.persistence.Column
import javax.persistence.Entity
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.reflect.KClass
import kotlin.reflect.KProperty1
import kotlin.reflect.full.memberProperties
import kotlin.reflect.jvm.javaField

fun entityCloneRun() {
    val cls = scanClasses(kotlinPackageName = kotlinPackageName)
    cls.forEach { cl ->
        val eAnno = cl.java.getAnnotation(Entity::class.java)
        if (eAnno != null) {
            cloneEntities.add(CloneEntity(kClass = cl, members = cl.memberProperties))
        }
    }
    Path("$kotlinGeneratorRootPath/server_generator/output").createDirectories()
    File("$kotlinGeneratorRootPath/server_generator/output/EntityClone.kt").writeText(entityCloneContent())
}

val cloneEntities = arrayListOf<CloneEntity>()

class CloneEntity(
        val kClass: KClass<*>,
        val members: Collection<KProperty1<out Any, *>>,
)

fun entityCloneContent(): String {
    var bodyContent = ""
    cloneEntities.forEach { ce ->
        var constructorContent = ""
        var toEntityContent = ""
        var toCloneContent = ""
        ce.members.forEach { m ->
            val isOnlyClient = m.name.contains(Regex("^(client_)"))
            val isNullable = m.javaField!!.getAnnotation(Column::class.java)!!.nullable
            val isNullableStr = if (isNullable) "?" else ""
            constructorContent += "        var ${m.name}: ${m.returnType.toString().removeSuffix("?")}$isNullableStr,\n"

            toEntityContent += "            it.${m.name} = ${m.name}\n"

            toCloneContent += "            ${m.name} = this.${m.name}${if (isOnlyClient) "" else (if (isNullable) "" else "!!")},\n"
        }
        bodyContent += """
class ${ce.kClass.simpleName}Clone(
$constructorContent
        ) {
    fun toEntity(): ${ce.kClass.qualifiedName} {
        return ${ce.kClass.qualifiedName}().also {
$toEntityContent
        }
    }
}

fun ${ce.kClass.qualifiedName}.toClone(): ${ce.kClass.simpleName}Clone {
    return ${ce.kClass.simpleName}Clone(
$toCloneContent
            )
}
"""
    }
    return "package com.example.demo.server_generator.output\n$bodyContent"
}