package com.example.demo.services

import org.springframework.stereotype.Service

@Service
class UniteService(
        val insertService: InsertService,
        val queryService: QueryService,
)