package com.example.demo.entity.local

import com.example.demo.entity.base.BaseIdManualAssignable
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import javax.persistence.Column

@ClientTable
class AppInfos : BaseIdManualAssignable() {
    @ClientColumn
    @Column(nullable = false)
    var token: String? = null

    @ClientColumn
    @Column(nullable = false)
    var hasDownloadedInitData: Boolean? = null
}