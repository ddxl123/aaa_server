
package com.example.demo.share_generate_result.dto_vo
import kotlin.Int
import kotlin.Long
import com.example.demo.entity.monolayer_group.NewDisplayOrder
data class RegisterAndLoginWithUsernameVo(

    // 0-注册，1-登录
    var register_or_login: Int,

    // 
    var id: Long?,

    // 
    var new_display_order: NewDisplayOrder?,

)
