package com.example.demo.controller.controller

import cn.dev33.satoken.stp.StpUtil
import com.example.demo.controller.ResponseWrapper
import com.example.demo.controller.dto_vo.DeviceAndTokenBo
import com.example.demo.controller.dto_vo.RegisterOrLogin
import com.example.demo.controller.dto_vo.RegisterOrLoginType
import com.example.demo.controller.exception.DefiniteException
import com.example.demo.global.routeDoLogin
import com.example.demo.server_generator.output.toClone
import com.example.demo.services.UniteService
import com.example.demo.share_generate_result.dto_vo.RegisterOrLoginDto
import com.example.demo.share_generate_result.dto_vo.RegisterOrLoginVo
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.bind.annotation.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

/**
 * TODO: 用 redis 存储。
 */
val loginOrRegisterVerifyCodes: ConcurrentHashMap<String, Int> = ConcurrentHashMap()

val loginOrRegisterVerifyCodeExpires: ConcurrentHashMap<String, Long> = ConcurrentHashMap()

fun remove(email: String) {
    loginOrRegisterVerifyCodes.remove(email)
    loginOrRegisterVerifyCodeExpires.remove(email)
}

@RestController
@RequestMapping(routeDoLogin)
class RegisterAndLoginController(
        var uniteService: UniteService,
        var javaMailSender: JavaMailSender,
) {
    @PostMapping("/send_or_verify")
    fun sendOrVerify(@RequestBody registerAndLoginDto: RegisterOrLoginDto): ResponseWrapper {
        if (registerAndLoginDto.register_or_login_type == RegisterOrLoginType.email_send) {
            val expiresTime = 300000
            val verifyCode: Int = Random.nextInt(1000, 9999)
            javaMailSender.send(
                    SimpleMailMessage().apply {
                        setFrom("904084422@qq.com")
                        setTo(registerAndLoginDto.email!!)
                        setSubject("验证码")
                        setText(verifyCode.toString())
                    }
            )
            loginOrRegisterVerifyCodes[registerAndLoginDto.email!!] = verifyCode
            loginOrRegisterVerifyCodeExpires[registerAndLoginDto.email!!] = System.currentTimeMillis() + expiresTime
            return RegisterOrLogin.code100.toResponseWrapper()
        }

        if (registerAndLoginDto.register_or_login_type == RegisterOrLoginType.email_verify) {
            val verifyCode = loginOrRegisterVerifyCodes[registerAndLoginDto.email]
            if (verifyCode == registerAndLoginDto.verify_code) {
                remove(registerAndLoginDto.email!!)
                val oldUser = uniteService.queryService.findOneOrNullUserBy(registerAndLoginDto.email!!)?.toClone()
                if (oldUser == null) {
                    val newUser = uniteService.insertService.insertUser(registerAndLoginDto.email!!).toClone()
                    // 如果数据库的用户被清除，则实际为未登录，但可能 redis 中的该用户多个设备已登录的数据仍然存在，因此需要登出一下。
                    // 登出的是全部设备。
                    StpUtil.logout(newUser.id, null)
                    // 登录的是当前设备。
                    StpUtil.login(newUser.id, registerAndLoginDto.device_info!!)
                    return RegisterOrLogin.code102.toResponseWrapper(RegisterOrLoginVo(
                            register_or_login_type = RegisterOrLoginType.email_verify,
                            be_new_user = true,
                            user_entity = newUser.toEntity(),
                            device_and_token_bo = DeviceAndTokenBo(
                                    deviceInfo = registerAndLoginDto.device_info!!,
                                    token = StpUtil.getTokenValueByLoginId(newUser.id, registerAndLoginDto.device_info!!)!!,
                            ),
                            device_and_token_bo_list = null
                    ))
                } else {
                    StpUtil.login(oldUser.id, registerAndLoginDto.device_info!!)
                    val userSession = StpUtil.getSessionByLoginId(oldUser.id).tokenSignList.map { DeviceAndTokenBo(deviceInfo = it.device, token = it.value) }
                    return RegisterOrLogin.code102.toResponseWrapper(RegisterOrLoginVo(
                            register_or_login_type = RegisterOrLoginType.email_verify,
                            be_new_user = false,
                            user_entity = oldUser.toEntity(),
                            device_and_token_bo = userSession.last(),
                            device_and_token_bo_list = userSession.toMutableList().apply { removeLast() }.toTypedArray()
                    ))
                }
            } else {
                return RegisterOrLogin.code101.toResponseWrapper()
            }
        }
        throw DefiniteException("未处理 RegisterAndLoginDto 类型: ${registerAndLoginDto.register_or_login_type}")
    }

}