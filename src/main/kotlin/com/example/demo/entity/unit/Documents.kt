package com.example.demo.entity.unit

import com.example.demo.entity.base.BaseIdManualAssignable
import javax.persistence.Column
import javax.persistence.Entity

/**
 * 文档不能被继承。
 */
@Entity
class Documents : BaseIdManualAssignable() {

    /**
     * 哪个用户创建的数据。
     */
    @Column(nullable = false)
    var creatorUserId: Long? = null

    /**
     * 文档内容。可以是文章、文件、富文本(可嵌入文件)
     */
    @Column(nullable = true)
    var content: String? = null
}