package com.example.demo.entity.r

import com.example.demo.entity.Users
import com.example.demo.entity.base.BaseIdManualAssignable
import com.example.demo.entity.unit.Documents
import com.example.demo.entity.unit_group.DocumentGroups
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import javax.persistence.Column
import javax.persistence.Entity

/**
 * [Documents] 属于 [DocumentGroups]
 */
@ClientTable
@Entity
class RDocument2DocumentGroups : BaseIdManualAssignable() {

    /**
     * 哪个用户创建的数据。
     */
    @ClientColumn(referenceTo = [Users::class])
    @Column(nullable = false)
    var creatorUserId: Long? = null

    /**
     * 关联的文档组 id。
     *
     * 若为空，则当前文档处在该用户文档组的最顶层。
     */
    @ClientColumn(referenceTo = [DocumentGroups::class])
    @Column(nullable = true)
    var documentGroupId: String? = null

    /**
     * 关联的文档 id。
     */
    @ClientColumn(referenceTo = [Documents::class])
    @Column(nullable = false)
    var documentId: String? = null

}