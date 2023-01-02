package com.example.demo.entity.unit

import com.example.demo.entity.Users
import com.example.demo.entity.base.BaseIdManualAssignable
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import javax.persistence.Column
import javax.persistence.Entity

/**
 * 笔记可以被继承。
 */
@ClientTable
@Entity
class Notes : BaseIdManualAssignable() {

    /**
     * 哪个用户创建的数据。
     */
    @ClientColumn(referenceTo = [Users::class])
    @Column(nullable = false)
    var creatorUserId: Long = -1

    /**
     * 从哪个文档摘取的笔记。
     * 若为 null，则为独立笔记。
     */
    @ClientColumn(referenceTo = [Documents::class])
    @Column(nullable = true)
    var documentId: String? = null

    /**
     * 该笔记是由哪个父笔记修改而来的。
     * 若为 null，则自身为根笔记。
     */
    @ClientColumn(referenceTo = [Notes::class])
    @Column(nullable = true)
    var fatherNoteId: String? = null

    /**
     * 富文本笔记内容(可嵌入文件)
     */
    @ClientColumn
    @Column(nullable = false)
    var content: String = ""
}
