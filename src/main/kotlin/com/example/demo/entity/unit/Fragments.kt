package com.example.demo.entity.unit

import com.example.demo.entity.Users
import com.example.demo.entity.base.BaseIdManualAssignable
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Column
import javax.persistence.Entity

/**
 * 碎片可以被继承。
 */
@ClientTable
@Entity
class Fragments : BaseIdManualAssignable() {

    /**
     * 哪个用户创建的数据。
     */
    @ClientColumn(referenceTo = [Users::class])
    @Column(nullable = false)
    var creatorUserId: Long? = null

    /**
     * 从哪个笔记碎片化的。
     * 若为 null，则为独立碎片。
     */
    @ClientColumn(referenceTo = [Notes::class])
    @Column(nullable = true)
    var noteId: String? = null

    /**
     * 从哪个父碎片修改而来的。
     * 若为 null，则自身为根碎片。
     */
    @ClientColumn(referenceTo = [Fragments::class])
    @Column(nullable = true)
    var fatherFragmentId: String? = null

    /**
     * 富文本碎片内容(可嵌入文件)
     */
    @ClientColumn
    @Column(nullable = false)
    var content: String? = null

    /**
     * 该行碎片是否被选择。
     */
    @Transient
    @ClientColumn
    @Column(nullable = false)
    var isSelected: Boolean? = null
}