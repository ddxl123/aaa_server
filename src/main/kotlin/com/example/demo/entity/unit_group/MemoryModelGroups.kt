package com.example.demo.entity.unit_group

import com.example.demo.entity.Users
import com.example.demo.entity.base.BaseIdManualAssignable
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import javax.persistence.Column
import javax.persistence.Entity

@ClientTable
@Entity
class MemoryModelGroups : BaseIdManualAssignable() {

    /**
     * 哪个用户创建的数据。
     */
    @ClientColumn(referenceTo = [Users::class])
    @Column(nullable = false)
    var creatorUserId: Long? = null

    /**
     * 当前记忆模型组的父记忆模型组id。
     * 若为 null，则当前记忆模型组是根记忆模型组。
     */
    @ClientColumn(referenceTo = [MemoryModelGroups::class])
    @Column(nullable = true)
    var fatherMemoryModelGroupsId: Long? = null

    /**
     * 记忆模型组名称。
     */
    @ClientColumn
    @Column(nullable = true)
    var title: String? = null
}