//package com.example.demo.controller.controller
//
//import com.example.demo.entity.Users
//import com.example.demo.repository.NotesRepository
//import com.example.demo.repository.UsersRepository
//import com.example.demo.controller.ResponseWrapper
//import com.example.demo.controller.dto_vo.RegisterAndLoginWithUsername
//import com.example.demo.share_generate_result.dto_vo.RegisterAndLoginWithUsernameDto
//import com.example.demo.share_generate_result.dto_vo.RegisterAndLoginWithUsernameVo
//import org.springframework.data.domain.Example
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//import java.time.Instant
//
//@RestController
//@RequestMapping("/register_and_login")
//class RegisterAndLoginController(
//        var usersRepository: UsersRepository,
//        var notesRepository: NotesRepository,
//) {
//
//    @PostMapping("/with_username")
//    fun withUsername(dto: RegisterAndLoginWithUsernameDto): ResponseWrapper {
//
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
//        return RegisterAndLoginWithUsername.code1.toResponseWrapper(
//                RegisterAndLoginWithUsernameVo(
//                        register_or_login = registerOrLogin,
//                        id = userResult.id
//                )
//        )
//    }
//}