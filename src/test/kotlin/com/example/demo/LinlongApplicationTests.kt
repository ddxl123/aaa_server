package com.example.demo

import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class LinlongApplicationTests {

    val logger = LoggerFactory.getLogger(LinlongApplicationTests::class.java)

    @Test
    fun contextLoads() {
        logger.error("顶顶顶顶顶顶顶顶顶顶顶顶")
    }

}
