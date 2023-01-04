package com.example.demo.controller.dto_vo

import com.example.demo.entity.Users
import com.example.demo.controller.CodeMessage
import com.example.demo.entity.r.RDocument2DocumentGroups
import com.example.demo.entity.two_way.ServerSyncInfos
import com.example.demo.share_generator.dto_vo_generator.annotation.DtoVo
import com.example.demo.share_generator.dto_vo_generator.toFieldTarget

enum class RegisterOrLoginType {
    email_send,
    email_verify,
    phone_send,
    phone_verify;
}

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
        )

        val vos = arrayListOf(
                "register_or_login_type".toFieldTarget(kotlinType = RegisterOrLoginType::class, isForceNullable = false),
                "be_new_user".toFieldTarget(kotlinType = Boolean::class, isForceNullable = false, explain = "当前用户是否为新用户"),
                "be_logged_in".toFieldTarget(kotlinType = Boolean::class, isForceNullable = true, explain = "是否用户状态是否已登录"),
                ServerSyncInfos::recentSyncTime.toFieldTarget(isForceNullable = true),
                Users().toFieldTarget(isForceNullable = true),
//                "token".toFieldTarget(kotlinType = String::class, isForceNullable = true, explain = "已在其他地方登录则为空，否则进行登录并带上token"),
        )
    }
}


