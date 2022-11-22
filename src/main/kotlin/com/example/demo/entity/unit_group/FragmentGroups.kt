package com.example.demo.entity.unit_group

import com.example.demo.entity.Users
import com.example.demo.entity.base.BaseIdManualAssignable
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import javax.persistence.Column
import javax.persistence.Entity

@ClientTable
@Entity
class FragmentGroups : BaseIdManualAssignable() {

    /**
     * 哪个用户创建的数据。
     */
    @ClientColumn(referenceTo = [Users::class])
    @Column(nullable = false)
    var creatorUserId: Long? = null

    /**
     * 当前碎片组的父碎片组id。
     * 若为 null，则当前碎片组是根碎片组。
     */
    @ClientColumn(referenceTo = [FragmentGroups::class])
    @Column(nullable = true)
    var fatherFragmentGroupsId: Long? = null

    /**
     * 碎片组名称。
     */
    @ClientColumn
    @Column(nullable = true)
    var title: String? = null
}