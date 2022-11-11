package com.example.demo.entity.r

import com.example.demo.entity.base.BaseEntity
import javax.persistence.Column
import javax.persistence.Entity
import com.example.demo.entity.unit.Fragments
import com.example.demo.entity.unit_group.FragmentGroups

/**
 * [Fragments] 属于 [FragmentGroups]
 */
@Entity
class RFragment2FragmentGroups : BaseEntity() {

    /**
     * 关联的碎片组 id。
     */
    @Column(nullable = false)
    var fragmentGroupId: Long? = null

    /**
     * 关联的碎片 id。
     */
    @Column(nullable = false)
    var fragmentId: Long? = null

}