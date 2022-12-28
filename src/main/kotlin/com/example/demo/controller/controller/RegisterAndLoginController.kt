package com.example.demo.controller.controller

import com.example.demo.entity.Users
import com.example.demo.repository.NotesRepository
import com.example.demo.repository.UsersRepository
import com.example.demo.controller.ResponseWrapper
import com.example.demo.controller.dto_vo.RegisterAndLoginWithUsername
import com.example.demo.entity.unit.NewDisplayOrder
import com.example.demo.share_generate_result.dto_vo.RegisterAndLoginWithUsernameDto
import com.example.demo.share_generate_result.dto_vo.RegisterAndLoginWithUsernameVo
import org.springframework.data.domain.Example
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.time.Instant

@RestController
@RequestMapping("/register_and_login")
class RegisterAndLoginController(
        var usersRepository: UsersRepository,
        var notesRepository: NotesRepository,
        var javaMailSender: JavaMailSender,
) {

    @PostMapping("/")
    fun withUsername(): ResponseWrapper {
        val s = SimpleMailMessage()
        s.setFrom("904084422@qq.com")
        s.setTo("1033839760@qq.com")
        s.setSubject("主题")
        s.setText("内容")
        javaMailSender.send(s)
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
        return RegisterAndLoginWithUsername.code1.toResponseWrapper(
                RegisterAndLoginWithUsernameVo(
                        register_or_login = 1,
                        id = 111,
                        new_display_order = NewDisplayOrder.random,
                )
        )
    }
}