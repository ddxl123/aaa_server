
package com.example.demo.share_generate_result.dto_vo
/**
 * [com.example.demo.controller.dto_vo.RegisterOrLogin]
 */
class RegisterOrLoginDto(

    // 
    var register_or_login_type: com.example.demo.controller.dto_vo.RegisterOrLoginType,

    // 
    var email: kotlin.String?,

    // 
    var phone: kotlin.String?,

    // 
    var verify_code: kotlin.Int?,

    // 必须带上设备，以便鉴别多设备登录！
    var device: kotlin.String,

)
