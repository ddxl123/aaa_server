package com.example.demo.entity.test

import com.example.demo.entity.base.BaseIdLocal
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import javax.persistence.Column
import javax.persistence.Entity

@ClientTable
@Entity
class Test2s : BaseIdLocal() {
    @Transient
    @ClientColumn(isOnlyLocal = true)
    @Column(nullable = false)
    var local_content: String? = null
}