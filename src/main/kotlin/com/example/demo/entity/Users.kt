package com.example.demo.entity

import com.example.demo.entity.base.BaseIdAutoAssignable
import com.example.demo.entity.base.BaseIdManualAssignable
import com.example.demo.entity.base.BaseTimeEntity
import com.example.demo.entity.unit_group.NoteGroups
import com.example.demo.share_generator.annotation.ClientColumn
import com.example.demo.share_generator.annotation.ClientTable
import javax.persistence.*


@ClientTable
@Entity
class Users : BaseIdAutoAssignable() {
    @ClientColumn
    @Column(nullable = true, length = 20)
    var username: String? = null

    @ClientColumn
    @Column(nullable = true, length = 20)
    var password: String? = null

    @ClientColumn
    @Column(nullable = true, length = 20)
    var email: String? = null

    @ClientColumn
    @Column(nullable = true)
    var age: Byte? = null
}

