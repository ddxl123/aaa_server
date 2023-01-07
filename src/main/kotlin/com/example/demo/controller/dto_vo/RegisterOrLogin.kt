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
        val deviceInfo: String,
        val token: String,
)

// 1 00 00
// 文件 控制器 原子
@DtoVo
class SendOrVerify {
    companion object {
        val code10101 = CodeMessage(10101, "验证码发送成功！", isRequiredData = false)
        val code10102 = CodeMessage(10102, "验证码不正确！", isRequiredData = false)
        val code10103 = CodeMessage(10103, "验证成功！", isRequiredData = true)

        val dtos = arrayListOf(
                "register_or_login_type".toFieldTarget(kotlinType = RegisterOrLoginType::class, isForceNullable = false),
                Users::email.toFieldTarget(isForceNullable = true),
                Users::phone.toFieldTarget(isForceNullable = true),
                "verify_code".toFieldTarget(kotlinType = Int::class, isForceNullable = true),
                DeviceAndTokenBo::deviceInfo.toFieldTarget(
                        isForceNullable = true,
                        explain = "验证验证码成功后，必须带上设备数据进行登录/注册，以便鉴别多设备登录！",
                ),
        )

        val vos = arrayListOf(
                "register_or_login_type".toFieldTarget(kotlinType = RegisterOrLoginType::class, isForceNullable = false),
                "be_new_user".toFieldTarget(kotlinType = Boolean::class, isForceNullable = false, explain = "当前用户是否为新用户"),
                Users::class.toFieldTarget(isForceNullable = true),
                ("current" + DeviceAndTokenBo::class.simpleName).toFieldTarget(
                        kotlinType = DeviceAndTokenBo::class,
                        isForceNullable = false,
                        explain = "当前登录/注册状态时的数据",
                ),
                DeviceAndTokenBo::class.toListFieldTarget(
                        isListForceNullable = true,
                        isElementForceNullable = false,
                        explain = "注册状态时，当前会话产生的 token 放到 ClientSyncInfos.token 中, " +
                                "登录状态时，全部的 token 放到这里（不包含当前会话产生的 token）",
                ),
        )
    }
}

@DtoVo
class Logout {
    companion object {
        val code10201 = CodeMessage(10201, "已下线其他全部登录！", isRequiredData = false, explain = "当前登录 token 不进行下线。")
        val code10202 = CodeMessage(
                10202,
                "下线成功！",
                isRequiredData = false,
                explain = "只会下线指定 token，如果下线的是当前登录的 token，不使用该 code。"
        )
        val code10203 = CodeMessage(10203, "已取消本次登录！", isRequiredData = false, explain = "只下线当前登录的 token。")
        val code10204 = CodeMessage(10204, "退出账号成功！", isRequiredData = false, explain = "客户端主动下线。")
        val code10205 = CodeMessage(
                10205, "当前登录会话在服务端并未登录，因此您无权限对其进行下线！",
                isRequiredData = false,
                explain = "请求的 current_token 在服务端已被下线过！",
        )

        val dtos = arrayListOf(
                "be_active".toFieldTarget(
                        kotlinType = Boolean::class,
                        isForceNullable = false,
                        explain = "该下线操作是否为已登录用户的在当前 deviceInfo 下的主动下线行为。" +
                                "当前设备在其他地方被下线时为 false。" +
                                "【取消本次登录】时为 false。"),
                ("current" + DeviceAndTokenBo::class.simpleName!!).toFieldTarget(
                        kotlinType = DeviceAndTokenBo::class,
                        isForceNullable = false,
                        explain = "当前登录的会话",
                ),
                DeviceAndTokenBo::class.toFieldTarget(isForceNullable = true, explain = "将下线的 token。若为 null，则下线全部（除了当前会话）"),
        )
        val vos = arrayListOf(
                "ok".toFieldTarget(isForceNullable = false, kotlinType = Boolean::class)
        )
    }
}

@DtoVo
class CheckLogin {
    companion object {
        val code10301 = CodeMessage(10301, "已登录！", isRequiredData = false, explain = "用户已登录")
        val code10302 = CodeMessage(10302, "未登录！", isRequiredData = false, explain = "用户未登录")

        val dtos = arrayListOf(
                DeviceAndTokenBo::class.toFieldTarget(isForceNullable = false, explain = "检查 user 的同时，必须同时检查这个")
        )
        val vos = arrayListOf(
                "ok".toFieldTarget(isForceNullable = false, kotlinType = Boolean::class)
        )
    }
}

// TODO: 检查 code 是否重复。

// TODO: 无 vos 情况也可以进行写入。

// TODO: 把 dtos vos 放到非 companion object 中，这样减少服务器内存的使用。

// TODO: controller 接收的对象如果只有一个子对象，则请求会抛出异常:
// Type definition error: [simple type, class com.example.demo.share_generate_result.dto_vo.CheckLoginDto]; nested exception is com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Cannot construct instance of `com.example.demo.share_generate_result.dto_vo.CheckLoginDto` (no Creators, like default constructor, exist): cannot deserialize from Object value (no delegate- or property-based Creator)
// at [Source: (org.springframework.util.StreamUtils$NonClosingInputStream); line: 1, column: 2]