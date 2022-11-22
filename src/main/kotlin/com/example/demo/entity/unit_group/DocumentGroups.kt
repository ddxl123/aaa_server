package com.example.demo.entity.unit_group

import com.example.demo.entity.Users
import com.example.demo.entity.base.BaseIdManualAssignable
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import javax.persistence.Column
import javax.persistence.Entity

@ClientTable
@Entity
class DocumentGroups : BaseIdManualAssignable() {

    /**
     * 哪个用户创建的数据。
     */
    @ClientColumn(referenceTo = [Users::class])
    @Column(nullable = false)
    var creatorUserId: Long? = null

    /**
     * 当前文档组的父文档组id。
     * 若为 null，则当前文档组是根文档组。
     */
    @ClientColumn(referenceTo = [DocumentGroups::class])
    @Column(nullable = true)
    var fatherDocumentGroupsId: Long? = null

    /**
     * 文档组名称。
     */
    @ClientColumn
    @Column(nullable = true)
    var title: String? = null
}