package com.example.demo.entity.r

import com.example.demo.entity.Users
import com.example.demo.entity.base.BaseIdManualAssignable
import com.example.demo.entity.unit.MemoryModels
import com.example.demo.entity.unit_group.MemoryModelGroups
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import javax.persistence.Column
import javax.persistence.Entity

/**
 * [MemoryModels] 属于 [MemoryModelGroups]
 */
@ClientTable
@Entity
class RMemoryModel2MemoryModelGroups : BaseIdManualAssignable() {

    /**
     * 哪个用户创建的数据。
     */
    @ClientColumn(referenceTo = [Users::class])
    @Column(nullable = false)
    var creatorUserId: Long? = null

    /**
     * 关联的记忆模型组 id。
     *
     * 若为空，则当前记忆模型处在该用户记忆模型组的最顶层。
     */
    @ClientColumn(referenceTo = [MemoryModelGroups::class])
    @Column(nullable = true)
    var memoryModelGroupId: String? = null

    /**
     * 关联的记忆模型 id。
     */
    @ClientColumn(referenceTo = [MemoryModels::class])
    @Column(nullable = false)
    var memoryModelId: String? = null

}