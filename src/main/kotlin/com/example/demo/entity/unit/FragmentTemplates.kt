package com.example.demo.entity.unit

import com.example.demo.entity.Users
import com.example.demo.entity.base.BaseIdManualAssignable
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import javax.persistence.Column
import javax.persistence.Entity

enum class FragmentTemplateType {
    /**
     * 可以为任何形式的 rich。
     */
    richAny,
}

@ClientTable
@Entity
class FragmentTemplates  : BaseIdManualAssignable() {

    @ClientColumn(referenceTo = [Users::class])
    @Column(nullable = false)
    var ownerUserId: Long? = null

    /**
     * 模板类型。
     */
    @ClientColumn()
    @Column(nullable = false)
    var type: FragmentTemplateType? = null

    @ClientColumn()
    @Column(nullable = false)
    var content: String? = null
}