
package com.example.demo.share_generate_result.dto_vo
/**
 * [com.example.demo.controller.dto_vo.CheckLogin]
 */
class CheckLoginDto(

    // 检查 user 的同时，必须同时检查这个
    var device_and_token_bo: com.example.demo.controller.dto_vo.DeviceAndTokenBo,

    // 填充字段
    var dto_padding: kotlin.Boolean?,

)
