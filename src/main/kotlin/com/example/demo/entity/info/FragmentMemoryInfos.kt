package com.example.demo.entity.info

import com.example.demo.entity.base.BaseIdManualAssignable
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class FragmentMemoryInfos : BaseIdManualAssignable() {

    /**
     * 哪个用户创建的数据。
     */
    @Column(nullable = false)
    var creatorUserId: Long? = null

    /**
     * 属于哪个碎片组。
     */
    @Column(nullable = false)
    var memoryGroupId: Long? = null

    /**
     * 属于哪个碎片。
     */
    @Column(nullable = false)
    var fragmentId: Long? = null

    /**
     * 在当前记忆组内的，当前记录是否为当前碎片的最新记录。
     *
     * 在新纪录被创建的同时，需要把旧记录设为 false。
     *
     * 只要在当前记忆组内存在记录，最新的一个记录的 [isLatestRecord] 总是为 true。
     */
    @Column(nullable = false)
    var isLatestRecord: Boolean? = null

    /**
     * 下一次计划展示的时间点。
     *
     * 单位秒。
     *
     * 从记忆组启动时的时间点开始计算。
     */
    @Column(nullable = false)
    var nextPlanShowTime: Instant? = null


    /**
     * 当前实际展示的时间点。
     *
     * 单位秒。
     *
     * 从记忆组启动时的时间点开始计算。
     */
    @Column(nullable = false)
    var currentActualShowTime: Int? = null

    /**
     * 刚展示时的熟练度。
     */
    @Column(nullable = false)
    var showFamiliarity: Double? = null

    /**
     * 点击按钮的时间。
     *
     * 单位秒。
     *
     * 从记忆组启动时的时间点开始计算。
     */
    @Column(nullable = false)
    var clickTime: Int? = null

    /**
     * 点击按钮的按钮数值。
     */
    @Column(nullable = false)
    var clickValue: Double? = null
}