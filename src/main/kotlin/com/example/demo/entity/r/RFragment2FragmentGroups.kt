package com.example.demo.entity.r

import com.example.demo.entity.Users
import com.example.demo.entity.base.BaseIdManualAssignable
import javax.persistence.Column
import javax.persistence.Entity
import com.example.demo.entity.unit.Fragments
import com.example.demo.entity.unit_group.FragmentGroups
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable

/**
 * [Fragments] 属于 [FragmentGroups]
 */
@ClientTable
@Entity
class RFragment2FragmentGroups : BaseIdManualAssignable() {

    /**
     * 哪个用户创建的数据。
     */
    @ClientColumn(referenceTo = [Users::class])
    @Column(nullable = false)
    var creatorUserId: Long = -1

    /**
     * 关联的碎片组 id。
     *
     * 若为空，则当前碎片处在该用户碎片组的最顶层。
     */
    @ClientColumn(referenceTo = [FragmentGroups::class])
    @Column(nullable = true)
    var fragmentGroupId: String? = null

    /**
     * 关联的碎片 id。
     */
    @ClientColumn(referenceTo = [Fragments::class])
    @Column(nullable = false)
    var fragmentId: String = ""

}