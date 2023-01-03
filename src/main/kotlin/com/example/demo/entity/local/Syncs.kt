package com.example.demo.entity.local

import com.example.demo.entity.base.BaseIdLocal
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import javax.persistence.Column


/**
 * 后面不带 'ing' - 暂未执行上传
 * 后面带 'ing' - 正在上传中
 * 原因：在客户端上传数据的过程中，客户端可能被断掉，从而客户端未对服务器所上传成功的响应消息进行接受处理。（若是服务器断掉，则客户端会收到失败的响应）
 *
 * TODO: 带 'ing' 的处理方式：
 *  - 服务端对比 updatedAt。
 *  - 若相同，则服务端已同步过。
 *  - 若客户端晚于服务端，则需要重新进行同步。
 *  - 若客户端早于服务端， 则 1. 可能客户端、服务端时间被篡改；2. 该条数据在其他客户端已经被同步过了 TODO: 可依据此处设计多客户端登陆方案。
 */
enum class SyncCurdType {
    /**
     * 增
     */
    c,
    cing,

    /**
     * 改
     */
    u,
    uing,

    /**
     * 删-暂未执行上传
     */
    d,
    ding,
}

@ClientTable
class Syncs : BaseIdLocal() {

    @ClientColumn
    @Column(nullable = false)
    var syncTableName: String? = null

    @ClientColumn
    @Column(nullable = false)
    var rowId: String? = null

    @ClientColumn
    @Column(nullable = false)
    var syncCurdType: SyncCurdType? = null

    /**
     * 同组标识符，可以看 [SyncTag]。
     *
     * 当多行 sync 是由同一事务或同一组合的操作时, 需要对这些行设置相同的 tag。
     *
     * 当进行上传时, 会将具有相同 tag 的行, 组成一组进行上传，再由云端对该组进行事务操作。
     *
     * 仅对 [CloudTableBase] 的子类表生效。
     *
     * 每组 tag 具有唯一性.
     */
    @ClientColumn
    @Column(nullable = false)
    var tag: Int? = null
}