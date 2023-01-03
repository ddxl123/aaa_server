
package com.example.demo.share_generate_result.dto_vo
import com.example.demo.controller.dto_vo.RegisterOrLoginType
import kotlin.Boolean
import java.time.Instant
import kotlin.Long
import kotlin.String
/**
 * [com.example.demo.controller.dto_vo.RegisterOrLogin]
 */
data class RegisterOrLoginVo(

    // 
    var register_or_login_type: RegisterOrLoginType,

    // 当前用户是否为新用户
    var be_new_user: Boolean,

    // 是否用户状态是否已登录
    var be_logged_in: Boolean?,

    // 
    var recent_sync_time: Instant?,

    // 
    var id: Long?,

    // 已在其他地方登录则为空，否则进行登录并带上token
    var token: String?,

)
