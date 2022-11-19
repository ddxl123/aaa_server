package com.example.demo.entity.r

import com.example.demo.entity.base.BaseIdManualAssignable
import javax.persistence.Column
import javax.persistence.Entity
import com.example.demo.entity.unit.Fragments
import com.example.demo.entity.unit_group.FragmentGroups

/**
 * [Fragments] 属于 [FragmentGroups]
 */
@Entity
class RFragment2FragmentGroups : BaseIdManualAssignable() {

    /**
     * 哪个用户创建的数据。
     */
    @Column(nullable = false)
    var creatorUserId: Long? = null

    /**
     * 关联的碎片组 id。
     *
     * 若为空，则当前碎片处在该用户碎片组的最顶层。
     */
    @Column(nullable = false)
    var fragmentGroupId: Long? = null

    /**
     * 关联的碎片 id。
     */
    @Column(nullable = false)
    var fragmentId: Long? = null

}