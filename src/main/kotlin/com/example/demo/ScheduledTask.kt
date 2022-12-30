package com.example.demo

import com.example.demo.controller.controller.loginOrRegisterVerifyCodeExpires
import com.example.demo.controller.controller.loginOrRegisterVerifyCodes
import com.example.demo.global.logger
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@Configuration
@EnableScheduling
class ScheduledTask {
    /**
     * 每 5min 清除一次。
     */
    @Scheduled(fixedDelay = 300000)
    fun clearLoginOrRegisterVerifyCode() {
        logger.info("正在清除过期登录/注册验证码...")
        logger.info(loginOrRegisterVerifyCodes.toString())
        logger.info(loginOrRegisterVerifyCodeExpires.toString())
        val it = loginOrRegisterVerifyCodeExpires.keys.iterator()
        while (it.hasNext()) {
            val key = it.next()
            val exp = loginOrRegisterVerifyCodeExpires[key]
            if (exp == null) {
                loginOrRegisterVerifyCodeExpires.remove(key)
                loginOrRegisterVerifyCodes.remove(key)
            } else {
                if (exp < System.currentTimeMillis()) {
                    loginOrRegisterVerifyCodeExpires.remove(key)
                    loginOrRegisterVerifyCodes.remove(key)
                }
            }
        }
        logger.info(loginOrRegisterVerifyCodes.toString())
        logger.info(loginOrRegisterVerifyCodeExpires.toString())
        logger.info("清除过期登录/注册验证码完成。")
    }
}