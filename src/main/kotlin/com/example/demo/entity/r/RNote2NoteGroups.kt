package com.example.demo.entity.r

import com.example.demo.entity.Users
import com.example.demo.entity.base.BaseIdManualAssignable
import com.example.demo.entity.unit.Notes
import com.example.demo.entity.unit_group.NoteGroups
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import javax.persistence.Column
import javax.persistence.Entity

/**
 * [Notes] 属于 [NoteGroups]
 */
@ClientTable
@Entity
class RNote2NoteGroups : BaseIdManualAssignable() {

    /**
     * 哪个用户创建的数据。
     */
    @ClientColumn(referenceTo = [Users::class])
    @Column(nullable = false)
    var creatorUserId: Long = -1

    /**
     * 关联的笔记组 id。
     *
     * 若为空，则当前笔记处在该用户笔记组的最顶层。
     */
    @ClientColumn(referenceTo = [NoteGroups::class])
    @Column(nullable = true)
    var noteGroupId: String? = null

    /**
     * 关联的笔记 id。
     */
    @ClientColumn(referenceTo = [Notes::class])
    @Column(nullable = false)
    var noteId: String = ""

}