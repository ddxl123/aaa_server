package com.example.demo.global.config

import cn.dev33.satoken.jwt.StpLogicJwtForStateless
import cn.dev33.satoken.stp.StpLogic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * sa-token 的 jwt 配置。
 */
@Configuration
class SaTokenJwtConfigure {
    @Bean
    fun getStpLogicJwt(): StpLogic {
        return StpLogicJwtForStateless()
    }
}