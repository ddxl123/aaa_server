
package com.example.demo.share_generate_result.dto_vo
import com.example.demo.controller.dto_vo.RegisterOrLoginType
import kotlin.Boolean
import java.time.Instant
import kotlin.Long
import kotlin.String
data class RegisterOrLoginVo(

    // 
    var register_or_login_type: RegisterOrLoginType,

    // 该用户是否已注册过
    var be_registered: Boolean,

    // 是否用户状态是否已登录
    var be_logged_in: Boolean?,

    // 
    var recent_sync_time: Instant?,

    // 
    var id: Long?,

    // 
    var token: String?,

)
