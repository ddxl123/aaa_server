package com.example.demo.entity.unit

import com.example.demo.entity.Users
import com.example.demo.entity.base.BaseIdManualAssignable
import com.example.demo.entity.info.FragmentMemoryInfos
import com.example.demo.share_generator.client_table_generator.annotation.ClientColumn
import com.example.demo.share_generator.client_table_generator.annotation.ClientTable
import java.time.Instant
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Enumerated

/**
 * 新旧碎片展示先后顺序。
 */
enum class NewReviewDisplayOrder {
    none,

    /**
     * 随机混合
     */
    mix,

    /**
     * 优先新的，[FragmentPermanentMemoryInfo]中在当前记忆组中没有记录的。
     */
    newReview,

    /**
     * 优先复习，，[FragmentPermanentMemoryInfo]中在当前记忆组中存在记录的，无论熟练度是不是0。
     */
    reviewNew,
}

/**
 * 新碎片展示先后顺序。
 */
enum class NewDisplayOrder {
    none,

    /**
     * 随机顺序。
     */
    random,

    /**
     * 按照 [Fragments.title] 首字母 A~Z 顺序，会自动去掉空前缀。
     */
    titleA2Z,

    /**
     * 按照 [Fragments.createdAt] 创建时间早的先于晚的顺序。
     */
    createEarly2Late,
}

@ClientTable
@Entity
class MemoryGroups : BaseIdManualAssignable() {

    /**
     * 哪个用户创建的数据。
     */
    @ClientColumn(referenceTo = [Users::class])
    @Column(nullable = false)
    var creatorUserId: Long = -1

    /**
     * 记忆组名称。
     */
    @ClientColumn
    @Column(nullable = false)
    var title: String = ""

    /**
     * 使用的记忆模型 id。
     */
    @ClientColumn(referenceTo = [MemoryModels::class])
    @Column(nullable = true)
    var memoryModelId: String? = null

    /**
     * 新学数量。
     *
     * 每次新学完一个，都会将该值减去1
     */
    @ClientColumn(referenceTo = [FragmentMemoryInfos::class])
    @Column(nullable = false)
    var willNewLearnCount: Int = -1

    /**
     * 取用 [reviewInterval] 时间点内的复习碎片。
     *
     * 标准时间戳。
     */
    @ClientColumn
    @Column(nullable = false)
    var reviewInterval: Instant = Instant.now()

    /**
     * 新旧碎片展示先后顺序。
     */
    @ClientColumn
    @Enumerated
    @Column(nullable = false)
    var newReviewDisplayOrder: NewReviewDisplayOrder = NewReviewDisplayOrder.none

    /**
     * 新碎片展示先后顺序。
     */
    @ClientColumn
    @Enumerated
    @Column(nullable = false)
    var newDisplayOrder: NewDisplayOrder = NewDisplayOrder.none

    /**
     * 开始时间的时间点。
     *
     * 若未开始，则为 null。
     *
     * 标准时间戳。
     */
    @ClientColumn
    @Column(nullable = true)
    var startTime: Instant? = null
}