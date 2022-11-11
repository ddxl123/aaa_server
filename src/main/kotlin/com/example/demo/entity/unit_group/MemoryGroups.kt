package com.example.demo.entity.unit_group

import com.example.demo.entity.base.BaseWithCreatorEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class MemoryGroups : BaseWithCreatorEntity() {

    /**
     * 记忆组名称。
     */
    @Column(nullable = true)
    var title: String? = null


    TextColumn get memoryModelId => text().nullable()();

    TextColumn get title => text()();

    /// 新学数量
    ///
    /// 每次新学完一个，都会将该值减去1。
    IntColumn get willNewLearnCount => integer()();

    /// 取用 [reviewInterval] 时间点内的复习碎片。
    DateTimeColumn get reviewInterval => dateTime()();

    /// 新旧碎片展示先后顺序。
    IntColumn get newReviewDisplayOrder => intEnum<NewReviewDisplayOrder>()();

    /// 新碎片展示先后顺序。
    IntColumn get newDisplayOrder => intEnum<NewDisplayOrder>()();

    /// 开始时间的时间点。
    ///
    /// 若未开始，则为 null。
    /// 若已完成，则为 [DateTime.fromMicrosecondsSinceEpoch(0)]
    DateTimeColumn get startTime => dateTime().nullable()();

    /// 是否启用 [filterOutAlgorithm]
    BoolColumn get isEnableFilterOutAlgorithm => boolean()();

    /// 是否跟随记忆模型。
    BoolColumn get isFilterOutAlgorithmFollowMemoryModel => boolean()();

    /// 过滤碎片算法。
    TextColumn get filterOutAlgorithm => text()();

    /// 是否启用 [floatingAlgorithm]
    BoolColumn get isEnableFloatingAlgorithm => boolean()();

    /// 是否跟随记忆模型。
    BoolColumn get isFloatingAlgorithmFollowMemoryModel => boolean()();

    /// 悬浮算法。
    TextColumn get floatingAlgorithm => text()();
}