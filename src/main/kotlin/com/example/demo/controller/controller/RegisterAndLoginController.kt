package com.example.demo.controller.controller

import cn.dev33.satoken.stp.StpUtil
import cn.dev33.satoken.util.SaResult
import com.example.demo.repository.NotesRepository
import com.example.demo.repository.UsersRepository
import com.example.demo.controller.ResponseWrapper
import com.example.demo.controller.dto_vo.RegisterAndLogin
import com.example.demo.controller.dto_vo.RegisterAndLoginType
import com.example.demo.share_generate_result.dto_vo.RegisterAndLoginDto
import com.example.demo.share_generate_result.dto_vo.RegisterAndLoginVo
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

val loginOrRegisterVerifyCodes: ConcurrentHashMap<String, Int> = ConcurrentHashMap()

val loginOrRegisterVerifyCodeExpires: ConcurrentHashMap<String, Long> = ConcurrentHashMap()
@RestController
@RequestMapping("/register_and_login")
class RegisterAndLoginController(
        var usersRepository: UsersRepository,
        var notesRepository: NotesRepository,
        var javaMailSender: JavaMailSender,
) {

    @GetMapping("/do_login")
    fun doLogin(): SaResult? {
        StpUtil.login(1)
        val tokenInfo = StpUtil.getTokenInfo()
        return SaResult.data(tokenInfo)
    }

    @PostMapping("/email")
    fun withUsername(registerAndLoginDto: RegisterAndLoginDto): ResponseWrapper {
        if (registerAndLoginDto.register_and_login_type == RegisterAndLoginType.email_send) {
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
            loginOrRegisterVerifyCodeExpires[registerAndLoginDto.email!!] = System.currentTimeMillis() + 60000
            return RegisterAndLogin.code100.toResponseWrapper()
        }
//        val registerOrLogin: Int
//
//        val findResult = usersRepository.findOne(Example.of(Users().apply {
//            username = dto.username
//        }))
//
//        val userResult: Users
//        if (findResult.isEmpty) {
//            userResult = usersRepository.save(Users().also {
//                it.username = dto.username
//                it.password = dto.password
//                it.createdAt = Instant.now()
//                it.updatedAt = Instant.now()
//            })
//            registerOrLogin = 0
//        } else {
//            userResult = findResult.get()
//            registerOrLogin = 1
//        }
//
        return RegisterAndLogin.code101.toResponseWrapper(
                RegisterAndLoginVo(
                        is_registered = true,
                        register_and_login_type = RegisterAndLoginType.email_send,
                        id = 111,
                )
        )
    }
}