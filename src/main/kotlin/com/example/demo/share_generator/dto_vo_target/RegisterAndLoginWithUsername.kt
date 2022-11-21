package com.example.demo.share_generator.dto_vo_target

import com.example.demo.entity.Users
import com.example.demo.share_generator.CodeMessage
import com.example.demo.share_generator.annotation.DtoVo
import com.example.demo.share_generator.generator.toFieldTarget

@DtoVo
class RegisterAndLoginWithUsername {
    companion object {
        val code1 = CodeMessage(1, "登录/注册成功！", isRequiredData = true)

        val dtos = arrayListOf(
                Users::username.toFieldTarget(),
                Users::password.toFieldTarget(),
        )

        val vos = arrayListOf(
                "register_or_login".toFieldTarget(kotlinType = Int::class, isForceNullable = false, explain = "0-注册，1-登录"),
                Users::id.toFieldTarget(),
        )
    }
}


