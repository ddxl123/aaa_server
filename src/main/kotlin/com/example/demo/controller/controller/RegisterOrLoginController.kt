package com.example.demo.controller.controller

import cn.dev33.satoken.stp.StpUtil
import com.example.demo.controller.ResponseWrapper
import com.example.demo.controller.dto_vo.*
import com.example.demo.controller.exception.DefiniteException
import com.example.demo.global.routeDoLogin
import com.example.demo.server_generator.output.toClone
import com.example.demo.services.UniteService
import com.example.demo.share_generate_result.dto_vo.CheckLoginDto
import com.example.demo.share_generate_result.dto_vo.LogoutDto
import com.example.demo.share_generate_result.dto_vo.SendOrVerifyDto
import com.example.demo.share_generate_result.dto_vo.SendOrVerifyVo
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.bind.annotation.*
import java.util.concurrent.ConcurrentHashMap
import javax.servlet.http.HttpServletRequestWrapper
import kotlin.random.Random

/**
 * TODO: 用 redis 存储。
 */
val loginOrRegisterVerifyCodes: ConcurrentHashMap<String, Int> = ConcurrentHashMap()

val loginOrRegisterVerifyCodeExpires: ConcurrentHashMap<String, Long> = ConcurrentHashMap()

fun remove(email: String) {
    loginOrRegisterVerifyCodes.remove(email)
    loginOrRegisterVerifyCodeExpires.remove(email)
}

@RestController
@RequestMapping(routeDoLogin)
class RegisterAndLoginController(
        var uniteService: UniteService,
        var javaMailSender: JavaMailSender,
) {
    @PostMapping("/send_or_verify")
    fun sendOrVerify(@RequestBody sendOrVerifyDto: SendOrVerifyDto): ResponseWrapper {
        if (sendOrVerifyDto.register_or_login_type == RegisterOrLoginType.email_send) {
            val expiresTime = 300000
            val verifyCode: Int = Random.nextInt(1000, 9999)
            javaMailSender.send(
                    SimpleMailMessage().apply {
                        setFrom("904084422@qq.com")
                        setTo(sendOrVerifyDto.email!!)
                        setSubject("验证码")
                        setText(verifyCode.toString())
                    }
            )
            loginOrRegisterVerifyCodes[sendOrVerifyDto.email!!] = verifyCode
            loginOrRegisterVerifyCodeExpires[sendOrVerifyDto.email!!] = System.currentTimeMillis() + expiresTime
            return SendOrVerify.code10101.toResponseWrapper()
        }

        if (sendOrVerifyDto.register_or_login_type == RegisterOrLoginType.email_verify) {
            val verifyCode = loginOrRegisterVerifyCodes[sendOrVerifyDto.email]
            if (verifyCode == sendOrVerifyDto.verify_code) {
                remove(sendOrVerifyDto.email!!)
                val oldUser = uniteService.queryService.findOneOrNullUserBy(sendOrVerifyDto.email!!)?.toClone()
                if (oldUser == null) {
                    val newUser = uniteService.insertService.insertUser(sendOrVerifyDto.email!!).toClone()
                    // 如果数据库的用户被清除，则实际为未登录，但可能 redis 中的该用户多个设备已登录的数据仍然存在，因此需要登出一下。
                    // 登出的是全部设备。
                    StpUtil.logout(newUser.id, null)
                    // 登录的是当前设备。
                    StpUtil.login(newUser.id, sendOrVerifyDto.device_info!!)
                    return SendOrVerify.code10103.toResponseWrapper(SendOrVerifyVo(
                            register_or_login_type = RegisterOrLoginType.email_verify,
                            be_new_user = true,
                            user_entity = newUser.toEntity(),
                            current_device_and_token_bo = DeviceAndTokenBo(
                                    deviceInfo = sendOrVerifyDto.device_info!!,
                                    token = StpUtil.getTokenValueByLoginId(newUser.id, sendOrVerifyDto.device_info!!)!!,
                            ),
                            device_and_token_bo_list = null
                    ))
                } else {
                    StpUtil.login(oldUser.id, sendOrVerifyDto.device_info!!)
                    val userSession = StpUtil.getSessionByLoginId(oldUser.id).tokenSignList.map { DeviceAndTokenBo(deviceInfo = it.device, token = it.value) }
                    return SendOrVerify.code10103.toResponseWrapper(SendOrVerifyVo(
                            register_or_login_type = RegisterOrLoginType.email_verify,
                            be_new_user = false,
                            user_entity = oldUser.toEntity(),
                            current_device_and_token_bo = userSession.last(),
                            device_and_token_bo_list = userSession.toMutableList().apply { removeLast() }.toTypedArray()
                    ))
                }
            } else {
                return SendOrVerify.code10102.toResponseWrapper()
            }
        }
        throw DefiniteException("未处理 RegisterAndLoginDto 类型: ${sendOrVerifyDto.register_or_login_type}")
    }

    @PostMapping("/logout")
    fun logout(@RequestBody logoutDto: LogoutDto): ResponseWrapper {
        val loginId = StpUtil.getLoginIdByToken(logoutDto.current_device_and_token_bo.token)
                ?: return Logout.code10205.toResponseWrapper()
        // 注销该用户 id 的全部设备
        if (logoutDto.device_and_token_bo == null) {
            StpUtil.getSessionByLoginId(loginId).tokenSignList.forEach {
                if (logoutDto.current_device_and_token_bo.deviceInfo != it.device) {
                    StpUtil.logoutByTokenValue(it.value)
                }
            }
            return Logout.code10201.toResponseWrapper()
        }

        if (logoutDto.current_device_and_token_bo.token == logoutDto.device_and_token_bo?.token) {
            StpUtil.logout(loginId, logoutDto.current_device_and_token_bo.deviceInfo)
            return if (logoutDto.be_active) Logout.code10204.toResponseWrapper() else Logout.code10203.toResponseWrapper()
        }
        StpUtil.logout(loginId, logoutDto.device_and_token_bo!!.deviceInfo)
        return Logout.code10202.toResponseWrapper()
    }

    @PostMapping("/check_login")
    fun checkLogin(@RequestBody checkLoginDto: CheckLoginDto): ResponseWrapper {
        val isLoggedIn = StpUtil.getLoginIdByToken(checkLoginDto.device_and_token_bo.token)
        return if (isLoggedIn == null) {
            CheckLogin.code10302.toResponseWrapper()
        } else {
            CheckLogin.code10301.toResponseWrapper()
        }
    }
}