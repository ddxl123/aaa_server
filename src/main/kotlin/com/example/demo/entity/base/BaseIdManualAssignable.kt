package com.example.demo.entity.base

import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
open class BaseIdManualAssignable : BaseTimeEntity() {
    /**
     * 需要手动分配 id，由客户端生成 id，在服务器进行手动赋值。
     *
     * 若不写 [GeneratedValue] id生成策略注解，才可以在业务中分配 id 值。
     */
    @ClientColumn
    @Id
    @Column(unique = true, nullable = false)
    var id: String = ""

}