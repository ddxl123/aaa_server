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
    var memoryGroupId: Long? = null

    /**
     * 属于哪个碎片。
     */
    @ClientColumn(referenceTo = [Fragments::class])
    @Column(nullable = false)
    var fragmentId: Long? = null

    /**
     * 在当前记忆组内的，当前记录是否为当前碎片的最新记录。
     *
     * 在新纪录被创建的同时，需要把旧记录设为 false。
     *
     * 只要在当前记忆组内存在记录，最新的一个记录的 [isLatestRecord] 总是为 true。
     */
    @ClientColumn
    @Column(nullable = false)
    var isLatestRecord: Boolean? = null

    /**
     * 下一次计划展示的时间点。
     *
     * 单位秒。
     *
     * 从记忆组启动时的时间点开始计算。
     */
    @ClientColumn
    @Column(nullable = false)
    var nextPlanShowTime: Instant? = null


    /**
     * 当前实际展示的时间点。
     *
     * 单位秒。
     *
     * 从记忆组启动时的时间点开始计算。
     */
    @ClientColumn
    @Column(nullable = false)
    var currentActualShowTime: Int? = null

    /**
     * 刚展示时的熟练度。
     */
    @ClientColumn
    @Column(nullable = false)
    var showFamiliarity: Double? = null

    /**
     * 点击按钮的时间。
     *
     * 单位秒。
     *
     * 从记忆组启动时的时间点开始计算。
     */
    @ClientColumn
    @Column(nullable = false)
    var clickTime: Int? = null

    /**
     * 点击按钮的按钮数值。
     */
    @ClientColumn
    @Column(nullable = false)
    var clickValue: Double? = null
}