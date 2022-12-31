package com.example.demo.controller.controller

import cn.dev33.satoken.stp.SaLoginConfig
import cn.dev33.satoken.stp.StpUtil
import com.example.demo.controller.ResponseWrapper
import com.example.demo.controller.dto_vo.RegisterAndLogin
import com.example.demo.controller.dto_vo.RegisterAndLoginType
import com.example.demo.controller.exception.CustomException
import com.example.demo.entity.Users
import com.example.demo.global.routeDoLogin
import com.example.demo.repository.NotesRepository
import com.example.demo.repository.UsersRepository
import com.example.demo.server_generator.output.Crt
import com.example.demo.share_generate_result.dto_vo.RegisterAndLoginDto
import com.example.demo.share_generate_result.dto_vo.RegisterAndLoginVo
import org.springframework.data.domain.Example
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
        var usersRepository: UsersRepository,
        var javaMailSender: JavaMailSender,
) {
    @PostMapping("/send_or_verify")
    fun sendOrVerify(@RequestBody registerAndLoginDto: RegisterAndLoginDto): ResponseWrapper {
        if (registerAndLoginDto.register_and_login_type == RegisterAndLoginType.email_send) {
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
            return RegisterAndLogin.code100.toResponseWrapper()
        }

        if (registerAndLoginDto.register_and_login_type == RegisterAndLoginType.email_verify) {
            val verifyCode = loginOrRegisterVerifyCodes[registerAndLoginDto.email]
            if (verifyCode == registerAndLoginDto.verify_code) {
                val oldUser = usersRepository.findOne(Example.of(Users().also { it.email = registerAndLoginDto.email }))
                if (oldUser.isEmpty) {
                    val newUser = usersRepository.save(Crt.users(
                            age = -1,
                            email = registerAndLoginDto.email,
                            password = null,
                            phone = null,
                            username = "还没有起名字",
                            createdAt = Instant.now(),
                            updatedAt = Instant.now(),
                    ))
                    StpUtil.login(newUser.id)
                    return RegisterAndLogin.code102.toResponseWrapper(RegisterAndLoginVo(
                            register_and_login_type = RegisterAndLoginType.email_verify,
                            is_registered = true,
                            id = newUser.id,
                            token = StpUtil.getTokenValue()
                    ))
                } else {
                    StpUtil.login(oldUser.get().id)
                    return RegisterAndLogin.code102.toResponseWrapper(RegisterAndLoginVo(
                            register_and_login_type = RegisterAndLoginType.email_verify,
                            is_registered = false,
                            id = oldUser.get().id,
                            token = StpUtil.getTokenValue()
                    ))
                }
            } else {
                return RegisterAndLogin.code101.toResponseWrapper()
            }
        }
        throw CustomException("未处理 RegisterAndLoginDto 类型: ${registerAndLoginDto.register_and_login_type}")
    }
}