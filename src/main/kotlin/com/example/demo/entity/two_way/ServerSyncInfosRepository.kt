package com.example.demo.entity.two_way

import org.springframework.data.domain.Example
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor
import org.springframework.data.repository.NoRepositoryBean
import java.util.*

interface ServerSyncInfosRepository : JpaRepository<ServerSyncInfos, Long>, JpaSpecificationExecutor<ServerSyncInfos> {
    /**
     * 根据 [userId] 查找。
     */
//    fun findBy(userId: Long): Optional<ServerSyncInfos> {
//        return findOne(Example.of(ServerSyncInfos().also { it.userId }))
//    }
}
