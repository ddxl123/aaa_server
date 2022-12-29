
package com.example.demo.share_generate_result.dto_vo
import com.example.demo.controller.dto_vo.RegisterAndLoginType
import kotlin.String
import kotlin.Int
data class RegisterAndLoginDto(

    // 
    var register_and_login_type: RegisterAndLoginType,

    // 
    var email: String?,

    // 
    var phone: String?,

    // 
    var verify_code: Int?,

)
