package com.example.demo.controller

import java.lang.Exception

data class CodeMessage(val code: Int, val message: String, val isRequiredData: Boolean) {
    fun toResponseWrapper(): ResponseWrapper {
        if (this.isRequiredData) {
            throw Exception("isRequiredData 为 true，但是执行的是不含 data 的函数！")
        }
        return ResponseWrapper(this.code, this.message, null)
    }

    fun toResponseWrapper(data: Any): ResponseWrapper {
        if (!this.isRequiredData) {
            throw Exception("isRequiredData 为 false，但是执行的是含 data 的函数！")
        }
        return ResponseWrapper(this.code, this.message, data)
    }
}
