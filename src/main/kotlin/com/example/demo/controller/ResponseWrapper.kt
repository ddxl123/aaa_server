package com.example.demo.controller

data class ResponseWrapper(
        val code: Int,
        val message: String,
        val data: Any?
)