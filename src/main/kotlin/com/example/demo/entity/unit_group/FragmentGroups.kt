package com.example.demo.entity.unit_group

import com.example.demo.entity.base.BaseIdManualAssignable
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class FragmentGroups : BaseIdManualAssignable() {

    /**
     * 哪个用户创建的数据。
     */
    @Column(nullable = false)
    var creatorUserId: Long? = null

    /**
     * 当前碎片组的父碎片组id。
     * 若为 null，则当前碎片组是根碎片组。
     */
    @Column(nullable = true)
    var fatherFragmentGroupsId: Long? = null

    /**
     * 碎片组名称。
     */
    @Column(nullable = true)
    var title: String? = null
}