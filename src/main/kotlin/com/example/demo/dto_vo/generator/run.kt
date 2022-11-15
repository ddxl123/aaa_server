package com.example.demo.dto_vo.generator

import com.example.demo.entity.Users
import com.example.demo.entity.unit.Fragments

fun main() {
    DtoVoGenerator().run()
}


val dtos = arrayOf(
        "RegisterAndLogin".toClassTarget(
                "register_or_login".toFieldTarget(
                        kotlinType = Int::class, isForceNullable = false, explain = "0-注册，1-登录"),
                Users::id.toFieldTarget(),
                Users::username.toFieldTarget(),
        ),
        "BBB".toClassTarget(
                Fragments::id.toFieldTarget()
        )
)
val bos = arrayOf(
        ClassTarget(
                "AAA",
                FieldTarget(Fragments::createdAt, false, ""),
        ),
        ClassTarget(
                "BBB",
                FieldTarget(Fragments::id, false, ""),
        ),
)