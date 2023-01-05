package com.example.demo.controller.dto_vo

import com.example.demo.entity.Users
import com.example.demo.controller.CodeMessage
import com.example.demo.share_generator.dto_vo_generator.annotation.Bo
import com.example.demo.share_generator.dto_vo_generator.annotation.DtoVo
import com.example.demo.share_generator.dto_vo_generator.toFieldTarget
import com.example.demo.share_generator.dto_vo_generator.toListFieldTarget

enum class RegisterOrLoginType {
    email_send,
    email_verify,
    phone_send,
    phone_verify;
}

@Bo
class DeviceAndTokenBo(
        val device: String,
        val token: String,
)

@DtoVo
class RegisterOrLogin {
    companion object {
        val code100 = CodeMessage(100, "验证码发送成功！", isRequiredData = false)
        val code101 = CodeMessage(101, "验证码不正确！", isRequiredData = false)
        val code102 = CodeMessage(102, "验证成功！", isRequiredData = true)

        val dtos = arrayListOf(
                "register_or_login_type".toFieldTarget(kotlinType = RegisterOrLoginType::class, isForceNullable = false),
                Users::email.toFieldTarget(isForceNullable = true),
                Users::phone.toFieldTarget(isForceNullable = true),
                "verify_code".toFieldTarget(kotlinType = Int::class, isForceNullable = true),
                "device".toFieldTarget(kotlinType = String::class, isForceNullable = false, explain = "必须带上设备，以便鉴别多设备登录！"),
        )

        val vos = arrayListOf(
                "register_or_login_type".toFieldTarget(kotlinType = RegisterOrLoginType::class, isForceNullable = false),
                "be_new_user".toFieldTarget(kotlinType = Boolean::class, isForceNullable = false, explain = "当前用户是否为新用户"),
                "be_exist_logged_in".toFieldTarget(kotlinType = Boolean::class, isForceNullable = true, explain = "当前用户是否以存在登录(可能在其他多个地方登录)"),
                Users::class.toFieldTarget(isForceNullable = true),
                DeviceAndTokenBo::class.toListFieldTarget(isListForceNullable = true, isElementForceNullable = false,
                        explain = "注册状态时，当前会话产生的 token 放到 User 实体中, " +
                                "登录状态时，全部的 token 放到这里（包含当前会话产生的 token）"),
        )
    }
}


