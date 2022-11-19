package com.example.demo.code_message

import com.example.demo.entity.Users
import com.example.demo.generator.toFieldTarget

class RegisterAndLoginWithUsername {
    companion object {
        val code1 = CodeMessage(1, "ccccccc", isRequiredData = true)
        val code2 = CodeMessage(2, "ccccccc", isRequiredData = false)

        val dtos = arrayListOf(
                Users::username.toFieldTarget(),
                Users::password.toFieldTarget()
        )

        val vos = arrayListOf(
                "register_or_login".toFieldTarget(kotlinType = Int::class, isForceNullable = false, explain = "0-注册，1-登录"),
                Users::id.toFieldTarget(),
        )
    }
}


