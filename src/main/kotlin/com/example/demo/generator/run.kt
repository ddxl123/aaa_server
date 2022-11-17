package com.example.demo.generator

import com.example.demo.entity.Users


fun main() {
    ShareObjectGenerator.setConfig(
            kotlinPackageName = "com.example.demo",
            kotlinGeneratorRootPath = "D:/project/aaa_server/src/main/kotlin/com/example/demo",
            dartGeneratorRootPath = "D:/project/aaa/subpackages/httper/lib",
            mainPath = "/share_object",
    )
    ShareObjectGenerator.targets.addAll(
            arrayOf(
                    ClassTarget(
                            PathClass(
                                    "",
                                    "",
                                    "RegisterAndLoginWithUsernameDto"
                            ),
                            Users::username.toFieldTarget(),
                            Users::password.toFieldTarget()
                    ),
                    ClassTarget(
                            PathClass(
                                    "",
                                    "",
                                    "RegisterAndLoginWithUsernameVo"
                            ),
                            "register_or_login".toFieldTarget(kotlinType = Int::class, isForceNullable = false, explain = "0-注册，1-登录"),
                            Users::id.toFieldTarget(),
                    )
            )
    )
    ShareObjectGenerator.run()
}

fun String.route(r: String): String {
    return "$this$r"
}

class Route(
        val route: String,
) {

    fun chain(route:Route): Route {
        return
    }

}


