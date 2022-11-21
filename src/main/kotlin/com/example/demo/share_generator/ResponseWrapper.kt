package com.example.demo.share_generator

data class ResponseWrapper(
        val code: Int,
        val message: String,
        val data: Any?
)