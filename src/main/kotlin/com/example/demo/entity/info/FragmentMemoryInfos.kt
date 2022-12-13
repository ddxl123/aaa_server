package com.example.demo.entity.info

import com.example.demo.entity.Users
import com.example.demo.entity.base.BaseIdManualAssignable
import com.example.demo.entity.monolayer_group.MemoryGroups
import com.example.demo.entity.unit.Fragments
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity

@ClientTable
@Entity
class FragmentMemoryInfos : BaseIdManualAssignable() {

    /**
     * 哪个用户创建的数据。
     */
    @ClientColumn(referenceTo = [Users::class])
    @Column(nullable = false)
    var creatorUserId: Long? = null

    /**
     * 属于哪个碎片组。
     */
    @ClientColumn(referenceTo = [MemoryGroups::class])
    @Column(nullable = false)
    var memoryGroupId: String? = null

    /**
     * 属于哪个碎片。
     */
    @ClientColumn(referenceTo = [Fragments::class])
    @Column(nullable = false)
    var fragmentId: String? = null

    /**
     * 记录下一次计划展示的时间点。
     *
     * 单位秒。
     *
     * 从记忆组启动时的时间点开始计算。
     *
     * 用 json 形式：[1,2,3]
     *
     * 为空表示没有记录。
     */
    @ClientColumn
    @Column(nullable = true)
    var nextPlanShowTime: String? = null


    /**
     * 记录当前实际展示的时间点。
     *
     * 单位秒。
     *
     * 从记忆组启动时的时间点开始计算。
     *
     * 用 json 形式：[1,2,3]
     *
     * 为空表示没有记录。
     */
    @ClientColumn
    @Column(nullable = true)
    var currentActualShowTime: String? = null

    /**
     * 记录刚展示时的熟练度。
     *
     * 类型：小数
     *
     * 用 json 形式：[1,2,3]
     *
     * 为空表示没有记录。
     */
    @ClientColumn
    @Column(nullable = true)
    var showFamiliarity: String? = null

    /**
     * 记录点击按钮的时间点。
     *
     * 单位秒。
     *
     * 从记忆组启动时的时间点开始计算。
     *
     * 用 json 形式：[1,2,3]
     *
     * 为空表示没有记录。
     */
    @ClientColumn
    @Column(nullable = true)
    var clickTime: String? = null

    /**
     * 记录点击按钮的按钮数值。
     *
     * 用 json 形式：[1,2,3]
     *
     * 为空表示没有记录。
     */
    @ClientColumn
    @Column(nullable = true)
    var clickValue: String? = null
}