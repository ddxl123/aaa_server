//package com.example.demo.controller
//
//import com.example.demo.entity.Users
//import com.example.demo.entity.unit.Fragments
//import com.example.demo.entity.unit.Notes
//import com.example.demo.repository.NotesRepository
//import com.example.demo.repository.UsersRepository
//import com.example.demo.share_object.RegisterAndLoginWithUsernameDto
//import com.example.demo.share_object.RegisterAndLoginWithUsernameVo
//import org.springframework.beans.factory.annotation.Autowired
//import org.springframework.boot.autoconfigure.security.SecurityProperties.User
//import org.springframework.data.domain.Example
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RestController
//import java.time.Instant
//import kotlin.random.Random
//
//@RestController
//@RequestMapping("/register_and_login")
//class RegisterAndLoginController(
//        var usersRepository: UsersRepository,
//        var notesRepository: NotesRepository,
//) {
//
//    @PostMapping("/with_username")
//    fun withUsername(dto: RegisterAndLoginWithUsernameDto): Any? {
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
//        println("userResult------:$userResult")
//
//        return ResponseWrapper(
//                code = 1,
//                message = "成功",
//                data = RegisterAndLoginWithUsernameVo(
//                        register_or_login = registerOrLogin,
//                        id = userResult.id
//                )
//        )
//    }
//}