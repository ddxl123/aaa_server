package com.example.demo.entity.monolayer_group

import com.example.demo.entity.base.BaseIdManualAssignable
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class MemoryGroups : BaseIdManualAssignable() {

    /**
     * 哪个用户创建的数据。
     */
    @Column(nullable = false)
    var creatorUserId: Long? = null

    /**
     * 记忆组名称。
     */
    @Column(nullable = true)
    var title: String? = null

    /**
     * 使用的记忆模型 id。
     */
    @Column(nullable = true)
    var memoryModelId: Long? = null

    /**
     * 新学数量。
     *
     * 每次新学完一个，都会将该值减去1
     */
    @Column(nullable = true)
    var willNewLearnCount: Int? = null

    /**
     * 取用 [reviewInterval] 时间点内的复习碎片。
     */
    @Column(nullable = true)
    var reviewInterval: Instant? = null

    /**
     * 新旧碎片展示先后顺序。
     */
    @Column(nullable = true)
    var newReviewDisplayOrder: Byte? = null

    /**
     * 新碎片展示先后顺序。
     */
    @Column(nullable = true)
    var newDisplayOrder: Byte? = null

    /**
     * 开始时间的时间点。
     *
     * 若未开始，则为 null。
     * 若已完成，则时间戳为0.
     */
    @Column(nullable = true)
    var startTime: Instant? = null
}