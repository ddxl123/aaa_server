package com.example.demo

import com.example.demo.controller.controller.loginOrRegisterVerifyCodeExpires
import com.example.demo.controller.controller.loginOrRegisterVerifyCodes
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.annotation.Scheduled

@EnableScheduling
class ScheduledTask {
    /**
     * 每 30s 清除一次。
     */
    @Scheduled(fixedDelay = 300000)
    fun clearLoginOrRegisterVerifyCode() {
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
    }
}