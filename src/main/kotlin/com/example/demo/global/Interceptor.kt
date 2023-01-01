package com.example.demo.global

import cn.dev33.satoken.interceptor.SaInterceptor
import cn.dev33.satoken.stp.StpUtil
import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.InterceptorRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer


@Configuration
class Interceptor : WebMvcConfigurer {
    override fun addInterceptors(registry: InterceptorRegistry) {
//        registry.addInterceptor(SaInterceptor { StpUtil.checkLogin() })
//                .addPathPatterns("$routeNeedLogin/**")
    }
}