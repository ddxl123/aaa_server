
package com.example.demo.share_generate_result.dto_vo
/**
 * [com.example.demo.controller.dto_vo.Logout]
 */
class LogoutDto(

    // 该下线操作是否为已登录用户的在当前 deviceInfo 下的主动下线行为。当前设备在其他地方被下线时为 false。【取消本次登录】时为 false。
    var be_active: kotlin.Boolean,

    // 当前登录的会话
    var current_device_and_token_bo: com.example.demo.controller.dto_vo.DeviceAndTokenBo,

    // 将下线的 token。若为 null，则下线全部（除了当前会话）
    var device_and_token_bo: com.example.demo.controller.dto_vo.DeviceAndTokenBo?,

)
