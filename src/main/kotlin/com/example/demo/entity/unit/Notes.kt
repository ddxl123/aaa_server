package com.example.demo.entity.unit

import com.example.demo.entity.base.BaseWithCreatorEntity
import javax.persistence.Column
import javax.persistence.Entity

/**
 * 笔记可以被继承。
 */
@Entity
class Notes : BaseWithCreatorEntity() {

    /**
     * 从哪个文档摘取的笔记。
     * 若为 null，则为独立碎片。
     */
    @Column(nullable = true)
    var documentId: Long? = null

    /**
     * 该笔记是由哪个父笔记修改而来的。
     * 若为 null，则自身为根笔记。
     */
    @Column(nullable = true)
    var fatherNoteId: Long? = null

    /**
     * 富文本笔记内容(可嵌入文件)
     */
    @Column(nullable = true)
    var content: String? = null
}
