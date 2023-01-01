
package com.example.demo.share_generate_result.dto_vo
import com.example.demo.controller.dto_vo.RegisterOrLoginType
import kotlin.Boolean
import kotlin.Long
import kotlin.String
data class RegisterOrLoginVo(

    // 
    var register_or_login_type: RegisterOrLoginType,

    // 是否为新注册用户
    var be_registered: Boolean,

    // 
    var id: Long?,

    // 
    var token: String?,

)
