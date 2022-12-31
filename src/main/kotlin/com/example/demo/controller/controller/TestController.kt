package com.example.demo.controller.controller

import com.example.demo.global.routeNeedLogin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("${routeNeedLogin}/test")
class TestController {

    @GetMapping("/get")
    fun getTest(): String {
        return "this is GET test"
    }

    @PostMapping("/post")
    fun postTest(): String {
        return "this is POST test"
    }
}