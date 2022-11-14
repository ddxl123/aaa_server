package com.example.demo.entity.r

import com.example.demo.entity.base.BaseWithCreatorIdEntity
import com.example.demo.entity.unit.Documents
import com.example.demo.entity.unit_group.DocumentGroups
import javax.persistence.Column
import javax.persistence.Entity

/**
 * [Documents] 属于 [DocumentGroups]
 */
@Entity
class RDocument2DocumentGroups : BaseWithCreatorIdEntity() {

    /**
     * 关联的文档组 id。
     *
     * 若为空，则当前文档处在该用户文档组的最顶层。
     */
    @Column(nullable = true)
    var documentGroupId: Long? = null

    /**
     * 关联的文档 id。
     */
    @Column(nullable = false)
    var documentId: Long? = null

}