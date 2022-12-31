
package com.example.demo.share_generate_result.dto_vo
import com.example.demo.controller.dto_vo.RegisterAndLoginType
import kotlin.Boolean
import kotlin.Long
import kotlin.String
data class RegisterAndLoginVo(

    // 
    var register_and_login_type: RegisterAndLoginType,

    // 是否为新注册用户
    var is_registered: Boolean,

    // 
    var id: Long?,

    // 
    var token: String?,

)
