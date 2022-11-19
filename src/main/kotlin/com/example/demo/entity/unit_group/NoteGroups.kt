package com.example.demo.entity.unit_group

import com.example.demo.entity.base.BaseIdManualAssignable
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class NoteGroups : BaseIdManualAssignable() {

    /**
     * 哪个用户创建的数据。
     */
    @Column(nullable = false)
    var creatorUserId: Long? = null

    /**
     * 当前笔记组的父笔记组id。
     * 若为 null，则当前笔记组是根笔记组。
     */
    @Column(nullable = true)
    var fatherNoteGroupsId: Long? = null

    /**
     * 笔记组名称。
     */
    @Column(nullable = true)
    var title: String? = null
}