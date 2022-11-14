package com.example.demo.entity.unit_group

import com.example.demo.entity.base.BaseWithCreatorIdEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class MemoryModelGroups : BaseWithCreatorIdEntity() {

    /**
     * 当前记忆模型组的父记忆模型组id。
     * 若为 null，则当前记忆模型组是根记忆模型组。
     */
    @Column(nullable = true)
    var fatherMemoryModelGroupsId: Long? = null

    /**
     * 记忆模型组名称。
     */
    @Column(nullable = true)
    var title: String? = null
}