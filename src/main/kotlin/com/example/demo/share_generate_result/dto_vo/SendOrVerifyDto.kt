
package com.example.demo.share_generate_result.dto_vo
/**
 * [com.example.demo.controller.dto_vo.SendOrVerify]
 */
class SendOrVerifyDto(

    // 
    var register_or_login_type: com.example.demo.controller.dto_vo.RegisterOrLoginType,

    // 
    var email: kotlin.String?,

    // 
    var phone: kotlin.String?,

    // 
    var verify_code: kotlin.Int?,

    // 验证验证码成功后，必须带上设备数据进行登录/注册，以便鉴别多设备登录！
    var device_info: kotlin.String?,

)
