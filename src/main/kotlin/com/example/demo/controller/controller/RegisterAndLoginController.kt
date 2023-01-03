package com.example.demo.controller.controller

import cn.dev33.satoken.stp.StpUtil
import com.example.demo.controller.ResponseWrapper
import com.example.demo.controller.dto_vo.RegisterOrLogin
import com.example.demo.controller.dto_vo.RegisterOrLoginType
import com.example.demo.controller.exception.DefiniteException
import com.example.demo.global.routeDoLogin
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
                val oldUser = uniteService.queryService.findOneOrNullUserBy(registerAndLoginDto.email!!)
                if (oldUser == null) {
                    val newUser = uniteService.insertService.insertUser(registerAndLoginDto.email!!)
                    StpUtil.login(newUser.id)
                    return RegisterOrLogin.code102.toResponseWrapper(RegisterOrLoginVo(
                            register_or_login_type = RegisterOrLoginType.email_verify,
                            be_new_user = true,
                            be_logged_in = true,
                            recent_sync_time = null,
                            id = newUser.id!!,
                            token = StpUtil.getTokenValueByLoginId(newUser.id!!)
                    ))
                } else {
                    val beLoggedIn = StpUtil.getTokenValueByLoginId(oldUser.id) != null
                    val serverSyncInfo = uniteService.queryService.findOneOrNullServerSyncInfoBy(oldUser.id!!)
                    if (!beLoggedIn) {
                        StpUtil.login(oldUser.id)
                    }
                    return RegisterOrLogin.code102.toResponseWrapper(RegisterOrLoginVo(
                            register_or_login_type = RegisterOrLoginType.email_verify,
                            be_new_user = false,
                            be_logged_in = beLoggedIn,
                            recent_sync_time = serverSyncInfo?.recentSyncTime,
                            id = oldUser.id,
                            token = if (beLoggedIn) null else StpUtil.getTokenValueByLoginId(oldUser.id),
                    ))
                }
            } else {
                return RegisterOrLogin.code101.toResponseWrapper()
            }
        }
        throw DefiniteException("未处理 RegisterAndLoginDto 类型: ${registerAndLoginDto.register_or_login_type}")
    }

}