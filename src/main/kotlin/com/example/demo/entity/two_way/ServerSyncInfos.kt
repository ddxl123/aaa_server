package com.example.demo.entity.two_way

import com.example.demo.entity.Users
import com.example.demo.entity.base.BaseIdAutoAssignable
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class ServerSyncInfos : BaseIdAutoAssignable() {

    @Column(nullable = false)
    var userId: Long? = null

    /**
     * 若客户端与服务端相同，则数据正常。
     *
     * 若客户端A存储的时间比服务端存储的时间靠前，说明用户在其他客户端B被同步，客户端A需要检测并下载，
     *
     * 若客户端A存储的时间比服务端存储的时间靠后，则抛出异常，并提醒需要清除数据重新下载。
     *
     * 若客户端A存储的时间比服务端存储的时间相同，则可以同步未同步内容。
     *
     * 若客户端没有存储的时间，则直接下载。
     */
    @Column(nullable = false)
    var recentSyncTime: Instant? = null
}