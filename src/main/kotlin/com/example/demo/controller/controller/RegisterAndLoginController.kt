package com.example.demo.controller.controller

import cn.dev33.satoken.stp.StpUtil
import com.example.demo.controller.ResponseWrapper
import com.example.demo.controller.dto_vo.RegisterOrLogin
import com.example.demo.controller.dto_vo.RegisterOrLoginType
import com.example.demo.controller.exception.CustomException
import com.example.demo.global.routeDoLogin
import com.example.demo.share_generate_result.dto_vo.RegisterOrLoginDto
import com.example.demo.share_generate_result.dto_vo.RegisterOrLoginVo
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.bind.annotation.*
import java.time.Instant
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

val loginOrRegisterVerifyCodes: ConcurrentHashMap<String, Int> = ConcurrentHashMap()

val loginOrRegisterVerifyCodeExpires: ConcurrentHashMap<String, Long> = ConcurrentHashMap()

@RestController
@RequestMapping(routeDoLogin)
class RegisterAndLoginController(
//        var simpleRepository: SimpleRepository,
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

//        if (registerAndLoginDto.register_or_login_type == RegisterOrLoginType.email_verify) {
//            val verifyCode = loginOrRegisterVerifyCodes[registerAndLoginDto.email]
//            if (verifyCode == registerAndLoginDto.verify_code) {
//                val oldUser = simpleRepository.usersRepository!!.findBy(registerAndLoginDto.email!!)
//                if (oldUser.isEmpty) {
////                    val newUser = simpleRepository.usersRepository.save(Crt.users(
////                            age = -1,
////                            email = registerAndLoginDto.email,
////                            password = null,
////                            phone = null,
////                            username = "还没有起名字",
////                            createdAt = Instant.now(),
////                            updatedAt = Instant.now(),
////                    ))
////                    StpUtil.login(newUser.id)
//                    return RegisterOrLogin.code102.toResponseWrapper(RegisterOrLoginVo(
//                            register_or_login_type = RegisterOrLoginType.email_verify,
//                            be_registered = false,
//                            be_logged_in = false,
//                            recent_sync_time = null,
//                            id = null,
//                            token = null
//                    ))
//                } else {
//                    val beLoggedIn = StpUtil.getTokenValueByLoginId(oldUser.get().id) != null
////                    val serverSyncInfo = simpleRepository.serverSyncInfosRepository.findBy(oldUser.get().id)
//                    return RegisterOrLogin.code102.toResponseWrapper(RegisterOrLoginVo(
//                            register_or_login_type = RegisterOrLoginType.email_verify,
//                            be_registered = true,
//                            be_logged_in = beLoggedIn,
////                            recent_sync_time = serverSyncInfo.get().recentSyncTime,
//                            recent_sync_time = Instant.now(),
//                            id = oldUser.get().id,
//                            token = StpUtil.getTokenValue()
//                    ))
//                }
//            } else {
//                return RegisterOrLogin.code101.toResponseWrapper()
//            }
//        }
        throw CustomException("未处理 RegisterAndLoginDto 类型: ${registerAndLoginDto.register_or_login_type}")
    }

}