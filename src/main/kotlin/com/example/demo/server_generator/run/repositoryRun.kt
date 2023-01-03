package com.example.demo.server_generator.run

import com.example.demo.share_generator.common.scanClasses
import com.example.demo.share_generator.route_generator.routeContent
import reactor.util.function.Tuple2
import java.io.File
import javax.persistence.Entity
import kotlin.io.path.Path
import kotlin.io.path.createDirectories
import kotlin.io.path.pathString
import kotlin.io.path.toPath
import kotlin.reflect.full.memberProperties

fun repositoryRun(kotlinPackageName: String, kotlinGeneratorRootPath: String) {
    val cls = scanClasses(kotlinPackageName = kotlinPackageName)
    cls.forEach { cl ->
        val eAnno = cl.java.getAnnotation(Entity::class.java)
        if (eAnno != null) {
            val path = cl.java.getResource("")!!.toURI().toPath().pathString
                    .replace("\\", "/")
                    .replace("build/classes/kotlin/main", "src/main/kotlin")
            val repositoryName = "${cl.simpleName}Repository"
            val file = File("$path/$repositoryName.kt")
            val pkg = path.split("src/main/kotlin/").last().replace("/", ".")
            val idTypeName = cl.memberProperties.find { it.name == "id" }!!.returnType.toString().removeSuffix("?").removePrefix("kotlin.")
            if (!file.exists()) {
                file.writeText(
                        repositoryContent(
                                pkg = pkg,
                                clName = cl.simpleName!!,
                                clRepositoryName = repositoryName,
                                idTypeName = idTypeName,
                        )
                )
            }
        }
    }

    Path("$kotlinGeneratorRootPath/services").createDirectories()
    File("$kotlinGeneratorRootPath/services/SimpleService.kt").writeText(serviceContent(kotlinPackageName = kotlinPackageName))
}


fun repositoryContent(pkg: String, clName: String, clRepositoryName: String, idTypeName: String): String {
    return """
package $pkg

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface $clRepositoryName : JpaRepository<$clName, $idTypeName>, JpaSpecificationExecutor<$clName>
"""
}

/**
 * 类名 - 包名+类名
 */
val repositoryList = arrayListOf<Pair<String, String>>()
fun serviceContent(kotlinPackageName: String): String {
    val cls = scanClasses(kotlinPackageName = kotlinPackageName)
    cls.forEach { cl ->
        if (cl.simpleName != null) {
            if (cl.simpleName!!.contains(Regex("(Repository)$"))) {
                repositoryList.add(
                        Pair(
                                cl.simpleName!!.replaceRange(0, 1, cl.simpleName!!.first().lowercase()),
                                cl.qualifiedName!!,
                        ))
            }
        }
    }
    var single = ""
    repositoryList.forEach {
        single += "        val ${it.first}: ${it.second},\n"
    }
    return """
package com.example.demo.services

@org.springframework.stereotype.Service
class SimpleService(
$single
)
"""
}
