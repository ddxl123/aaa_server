package com.example.demo.entity.r

import com.example.demo.entity.base.BaseEntity
import com.example.demo.entity.unit.Documents
import com.example.demo.entity.unit.Fragments
import com.example.demo.entity.unit_group.DocumentGroups
import com.example.demo.entity.unit_group.FragmentGroups
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

/**
 * [Documents] 属于 [DocumentGroups]
 */
@Entity
open class RDocument2DocumentGroups : BaseEntity() {

    /**
     * 关联的文档组 id。
     */
    @Column(nullable = false)
    var documentGroupId: Long? = null

    /**
     * 关联的文档 id。
     */
    @Column(nullable = false)
    var documentId: Long? = null

}