package com.example.demo.entity.unit_group

import com.example.demo.entity.Users
import com.example.demo.entity.base.BaseIdManualAssignable
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import javax.persistence.Column
import javax.persistence.Entity

@ClientTable
@Entity
class NoteGroups : BaseIdManualAssignable() {

    /**
     * 哪个用户创建的数据。
     */
    @ClientColumn(referenceTo = [Users::class])
    @Column(nullable = false)
    var creatorUserId: Long = -1

    /**
     * 当前笔记组的父笔记组id。
     * 若为 null，则当前笔记组是根笔记组。
     */
    @ClientColumn(referenceTo = [NoteGroups::class])
    @Column(nullable = true)
    var fatherNoteGroupsId: String? = null

    /**
     * 笔记组名称。
     */
    @ClientColumn
    @Column(nullable = false)
    var title: String = ""
}