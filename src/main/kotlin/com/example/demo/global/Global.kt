package com.example.demo.global

import com.example.demo.LinlongApplication
import org.slf4j.Logger
import org.slf4j.LoggerFactory

val logger: Logger = LoggerFactory.getLogger(LinlongApplication::class.java)

const val routeDoLogin = "/register_or_login"
const val routeNeedLogin = "/need_login"