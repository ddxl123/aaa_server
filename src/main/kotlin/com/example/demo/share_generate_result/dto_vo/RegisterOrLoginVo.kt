
package com.example.demo.share_generate_result.dto_vo
/**
 * [com.example.demo.controller.dto_vo.RegisterOrLogin]
 */
data class RegisterOrLoginVo(

    // 
    var register_or_login_type: com.example.demo.controller.dto_vo.RegisterOrLoginType,

    // 当前用户是否为新用户
    var be_new_user: kotlin.Boolean,

    // 是否用户状态是否已登录
    var be_logged_in: kotlin.Boolean?,

    // 
    var recent_sync_time: java.time.Instant?,

    // 
    var user_entity: com.example.demo.entity.Users?,

)
