package com.example.demo.entity.unit

import com.example.demo.entity.base.BaseWithCreatorEntity
import javax.persistence.Column
import javax.persistence.Entity

/**
 * 碎片可以被继承。
 */
@Entity
class Fragments : BaseWithCreatorEntity() {

    /**
     * 从哪个笔记碎片化的。
     * 若为 null，则为独立碎片。
     */
    @Column(nullable = true)
    var noteId: Long? = null

    /**
     * 从哪个父碎片修改而来的。
     * 若为 null，则自身为根碎片。
     */
    @Column(nullable = true)
    var fatherFragmentId: Long? = null

    /**
     * 富文本碎片内容(可嵌入文件)
     */
    @Column(nullable = true)
    var content: String? = null
}