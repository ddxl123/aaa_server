package com.example.demo.entity.client

import cn.dev33.satoken.stp.StpUtil
import com.example.demo.entity.Users
import com.example.demo.entity.base.BaseIdClient
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import java.time.Instant
import javax.persistence.Column

@ClientTable
class ClientSyncInfos : BaseIdClient() {
    /**
     * 相对应的服务端存储在 Sa-Token 的对应的 token-session 中。
     *
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
    @ClientColumn
    @Column(nullable = true)
    var recentSyncTime: Instant? = null

    /**
     * 该客户端数据库的唯一识别码。
     *
     * 每次登录都需要提供 [deviceInfo] 给 [StpUtil.login] 两个参数: userId - [Users.id] 和 device - [deviceInfo],
     * 以便解决同类型设备多开应用问题。
     */
    @ClientColumn
    @Column(nullable = false)
    var deviceInfo: String? = null


    @ClientColumn
    @Column(nullable = true)
    var token: String? = null
}
