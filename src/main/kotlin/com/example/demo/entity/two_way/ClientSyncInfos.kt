package com.example.demo.entity.two_way

import com.example.demo.entity.Users
import com.example.demo.entity.base.BaseIdAutoAssignable
import com.example.demo.entity.base.BaseIdLocal
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity

@ClientTable
class ClientSyncInfos : BaseIdLocal() {
    /**
     * 若客户端与服务端相同，则数据正常。
     *
     * 若客户端存储的时间比服务端存储的时间不一致，则需要删除客户端全部数据并重新下载全部数据，
     *
     * 若客户端没有存储的时间，则直接下载。
     */
    @ClientColumn
    @Column(nullable = false)
    var recentSyncTime: Instant? = null
}