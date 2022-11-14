package com.example.demo.entity.r

import com.example.demo.entity.base.BaseWithCreatorIdEntity
import com.example.demo.entity.unit.Notes
import com.example.demo.entity.unit_group.NoteGroups
import javax.persistence.Column
import javax.persistence.Entity

/**
 * [Notes] 属于 [NoteGroups]
 */
@Entity
class RNote2NoteGroups : BaseWithCreatorIdEntity() {

    /**
     * 关联的笔记组 id。
     *
     * 若为空，则当前笔记处在该用户笔记组的最顶层。
     */
    @Column(nullable = false)
    var noteGroupId: Long? = null

    /**
     * 关联的笔记 id。
      */
    @Column(nullable = false)
    var noteId: Long? = null

}