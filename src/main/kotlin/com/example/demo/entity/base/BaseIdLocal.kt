package com.example.demo.entity.base

import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import javax.persistence.*

@MappedSuperclass
open class BaseIdLocal : BaseTimeEntity() {

    /**
     * 纯 local 类型的 id，由客户端进行生成的自增类型，不会被同步到 cloud。
     */
    @ClientColumn
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(unique = true, nullable = false)
    var id: Long = -1

}