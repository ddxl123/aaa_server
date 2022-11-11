package com.example.demo.entity.r

import com.example.demo.entity.base.BaseEntity
import com.example.demo.entity.unit.Fragments
import com.example.demo.entity.unit.Notes
import com.example.demo.entity.unit_group.FragmentGroups
import com.example.demo.entity.unit_group.NoteGroups
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Table

/**
 * [Notes] 属于 [NoteGroups]
 */
@Entity
open class RNote2NoteGroups : BaseEntity() {

    /**
     * 关联的笔记组 id。
     */
    @Column(nullable = false)
    var noteGroupId: Long? = null

    /**
     * 关联的笔记 id。
     */
    @Column(nullable = false)
    var noteId: Long? = null

}