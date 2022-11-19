package com.example.demo.entity.r

import com.example.demo.entity.base.BaseIdManualAssignable
import com.example.demo.entity.unit.MemoryModels
import com.example.demo.entity.unit_group.MemoryModelGroups
import javax.persistence.Column
import javax.persistence.Entity

/**
 * [MemoryModels] 属于 [MemoryModelGroups]
 */
@Entity
class RMemoryModel2MemoryModelGroups : BaseIdManualAssignable() {

    /**
     * 哪个用户创建的数据。
     */
    @Column(nullable = false)
    var creatorUserId: Long? = null

    /**
     * 关联的记忆模型组 id。
     *
     * 若为空，则当前记忆模型处在该用户记忆模型组的最顶层。
     */
    @Column(nullable = false)
    var memoryModelGroupId: Long? = null

    /**
     * 关联的记忆模型 id。
     */
    @Column(nullable = false)
    var memoryModelId: Long? = null

}