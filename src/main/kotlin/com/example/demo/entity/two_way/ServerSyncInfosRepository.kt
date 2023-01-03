package com.example.demo.entity.two_way

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface ServerSyncInfosRepository : JpaRepository<ServerSyncInfos, Long>, JpaSpecificationExecutor<ServerSyncInfos>
