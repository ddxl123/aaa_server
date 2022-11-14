package com.example.demo.entity.unit_group

import com.example.demo.entity.base.BaseWithCreatorIdEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class DocumentGroups : BaseWithCreatorIdEntity() {

    /**
     * 当前文档组的父文档组id。
     * 若为 null，则当前文档组是根文档组。
     */
    @Column(nullable = true)
    var fatherDocumentGroupsId: Long? = null

    /**
     * 文档组名称。
     */
    @Column(nullable = true)
    var title: String? = null
}