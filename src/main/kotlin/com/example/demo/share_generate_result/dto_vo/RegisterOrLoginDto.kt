
package com.example.demo.share_generate_result.dto_vo
import com.example.demo.controller.dto_vo.RegisterOrLoginType
import kotlin.String
import kotlin.Int
data class RegisterOrLoginDto(

    // 
    var register_or_login_type: RegisterOrLoginType,

    // 
    var email: String?,

    // 
    var phone: String?,

    // 
    var verify_code: Int?,

)
