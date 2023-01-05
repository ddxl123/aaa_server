
package com.example.demo.share_generate_result.dto_vo
/**
 * [com.example.demo.controller.dto_vo.RegisterOrLogin]
 */
class RegisterOrLoginVo(

    // 
    var register_or_login_type: com.example.demo.controller.dto_vo.RegisterOrLoginType,

    // 当前用户是否为新用户
    var be_new_user: kotlin.Boolean,

    // 当前用户是否以存在登录(可能在其他多个地方登录)
    var be_exist_logged_in: kotlin.Boolean?,

    // 
    var user_entity: com.example.demo.entity.Users?,

    // 注册状态时，当前会话产生的 token 放到 User 实体中, 登录状态时，全部的 token 放到这里（包含当前会话产生的 token）
    var device_and_token_bo_list: kotlin.Array<com.example.demo.controller.dto_vo.DeviceAndTokenBo>?,

)
