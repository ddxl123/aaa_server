package com.example.demo.dto_vo.generator

import com.example.demo.entity.Users
import com.example.demo.entity.unit.Fragments
import com.example.demo.entity.unit.Notes
import java.time.Instant

fun main() {
    DtoVoGenerator().run()
}


val dtos = arrayOf(
        ClassTarget("RegisterAndLogin",
                arrayOf(
                        Variable(
                                variableName = "register_or_login",
                                kotlinType = Int::class,
                                isForceNullable = false,
                                explain = "0-注册，1-登录"
                        ),
                        Variable(
                                variableObj = Users::id,
                        ),
                        Variable(
                                variableObj = Users::username,
                        ),
                )),
        ClassTarget("BBB",
                arrayOf(
                        Variable(Fragments::id, false, ""),
                )),
)

val bos = arrayOf(
        ClassTarget("AAA",
                arrayOf(
                        Variable(Fragments::createdAt, false, ""),
                )),
        ClassTarget("BBB",
                arrayOf(
                        Variable(Fragments::id, false, ""),
                )),
)