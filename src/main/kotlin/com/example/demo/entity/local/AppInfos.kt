package com.example.demo.entity.local

import com.example.demo.entity.base.BaseIdLocal
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import javax.persistence.Column

@ClientTable
class AppInfos : BaseIdLocal() {
    @ClientColumn
    @Column(nullable = false)
    var token: String? = null

    @ClientColumn
    @Column(nullable = false)
    var hasDownloadedInitData: Boolean? = null
}