package com.example.demo.dto_vo.dto

data class RegisterAndLogin(

    // 0-注册，1-登录
    var register_or_login: Int,

    // 
    var id: Long?,

    // 
    var username: String?,

)