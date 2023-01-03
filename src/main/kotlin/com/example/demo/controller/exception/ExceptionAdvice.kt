package com.example.demo.controller.exception

import cn.dev33.satoken.error.SaErrorCode
import cn.dev33.satoken.exception.SaTokenException
import cn.hutool.json.JSONObject
import com.example.demo.controller.CodeMessage
import com.example.demo.controller.ResponseWrapper
import com.example.demo.global.logger
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@RestControllerAdvice
class ExceptionAdvice {
    companion object {
        val code10000 = CodeMessage(10000, "服务器出现未知异常！10000", isRequiredData = false)
        val code10001 = CodeMessage(10001, "服务器出现未知异常！10001", isRequiredData = false)
        val code10011 = CodeMessage(10011, "用户登录异常！", isRequiredData = false)
        val code10012 = CodeMessage(10012, "用户登录已过期！", isRequiredData = false)
        val code10013 = CodeMessage(10013, "用户在其他设备被登录！", isRequiredData = false)
        val code10014 = CodeMessage(10014, "该用户已被强制下线！", isRequiredData = false)
        val code10015 = CodeMessage(10015, "用户未登录！", isRequiredData = false)
    }

    @ExceptionHandler(SaTokenException::class)
    fun registerOrLoginExceptionHandler(saTokenException: SaTokenException): ResponseWrapper {
        if (saTokenException.code == SaErrorCode.CODE_11013) {
            return code10012.toResponseWrapper()
        }
        if (saTokenException.code == SaErrorCode.CODE_11014) {
            return code10013.toResponseWrapper()
        }
        if (saTokenException.code == SaErrorCode.CODE_11015) {
            return code10014.toResponseWrapper()
        }
        if (saTokenException.code == SaErrorCode.CODE_11011) {
            return code10015.toResponseWrapper()
        }
        logger.error("registerOrLoginExceptionHandler 截取到异常: ${saTokenException.message}", saTokenException)
        return code10011.toResponseWrapper()
    }


    @ExceptionHandler(DefiniteException::class)
    fun definiteExceptionHandler(definiteException: DefiniteException): ResponseWrapper {
        logger.error("definiteExceptionHandler 截取到异常: ${definiteException.message}", definiteException)
        return code10001.toResponseWrapper()
    }

    @ExceptionHandler(Throwable::class)
    fun otherExceptionHandler(request: HttpServletRequest, response: HttpServletResponse, throwable: Throwable): ResponseWrapper {
        val method = request.method
        val url = request.requestURL
        val header = mutableMapOf<String, String?>()
        val params = request.queryString
        val protocol = request.protocol
        val remote = request.remoteAddr + " " + request.remotePort

        val headerWrapper = request.headerNames.asIterator()
        while (headerWrapper.hasNext()) {
            val key = headerWrapper.next()
            header[key] = request.getHeader(key)
        }

        val allMap = mapOf<String, Any?>(
                Pair("method", method),
                Pair("url", url.toString()),
                Pair("header", header),
                Pair("params", params),
                Pair("protocol", protocol),
                Pair("remote", remote),
        )

        logger.error("otherExceptionHandler 截取到异常: ${throwable.message}\n客户端请求数据:\n${JSONObject().apply { putAll(allMap) }.toStringPretty()}", throwable)
        return code10000.toResponseWrapper()
    }
}