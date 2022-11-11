package com.example.demo.entity.base

import javax.persistence.Column
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class BaseWithCreatorEntity : BaseEntity() {

    /**
     * 哪个用户创建的数据。
     */
    @Column(nullable = false)
    var creatorId: Long? = null

}