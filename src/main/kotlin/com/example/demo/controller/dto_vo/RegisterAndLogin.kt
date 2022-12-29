package com.example.demo.controller.dto_vo

import com.example.demo.entity.Users
import com.example.demo.controller.CodeMessage
import com.example.demo.entity.unit.MemoryGroups
import com.example.demo.share_generator.dto_vo_generator.annotation.DtoVo
import com.example.demo.share_generator.dto_vo_generator.toFieldTarget

enum class RegisterAndLoginType(number: Int) {
    email_send(0),
    email_verify(1),
    phone_send(2),
    phone_verify(3),
}

@DtoVo
class RegisterAndLogin {
    companion object {
        val code100 = CodeMessage(100, "验证码发送成功！", isRequiredData = false)
        val code101 = CodeMessage(101, "登录/注册成功！", isRequiredData = true)

        val dtos = arrayListOf(
                "register_and_login_type".toFieldTarget(kotlinType = RegisterAndLoginType::class, isForceNullable = false),
                Users::email.toFieldTarget(isForceNullable = true),
                Users::phone.toFieldTarget(isForceNullable = true),
                "verify_code".toFieldTarget(kotlinType = Int::class, isForceNullable = true),
        )

        val vos = arrayListOf(
                "register_and_login_type".toFieldTarget(kotlinType = RegisterAndLoginType::class, isForceNullable = false),
                "is_registered".toFieldTarget(kotlinType = Boolean::class, isForceNullable = false, explain = "是否为新注册用户"),
                Users::id.toFieldTarget(isForceNullable = true),
        )
    }
}


