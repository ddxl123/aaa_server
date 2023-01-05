package com.example.demo.entity.test

import com.example.demo.entity.base.BaseIdClient
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import javax.persistence.Column
import javax.persistence.Entity

@ClientTable
@Entity
class Tests : BaseIdClient() {
    @Transient
    @ClientColumn(isOnlyClient = true)
    @Column(nullable = false)
    var client_content: String = ""
}