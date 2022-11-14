package com.example.demo.entity.unit

import com.example.demo.entity.base.BaseWithCreatorIdEntity
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class MemoryModels : BaseWithCreatorIdEntity() {

    /**
     * 从哪个父记忆模型修改而来的。
     * 若为 null，则自身为根记忆模型。
     */
    @Column(nullable = true)
    var fatherFragmentId: Long? = null

    @Column(nullable = true)
    var title: String? = null


    /**
     * <在点击按钮时>，评估碎片熟悉度变化的算法。
     * 每次展示并点击按钮后，将输入以下变量，计算出新的熟悉度函数曲线。
     * 注意：是 [每次展示并点击按钮 后 ，将输入以下变量]，而非 [每次点击按钮前，或每次展示前，或每次展示后且点击按钮前...]。
     * y=f(x) 是一个任意函数。
     * 1. 一般来讲，若熟悉度的范围为0%~100%，则需要将其表示为 0~1 或 0.0~1.0。例如，熟悉度 50%，则需要写成 0.5，否则%符号无法解析。
     * 2. 即使熟悉度可能存在范围，但它仍然可以超出范围，例如 -0.5, 1.5...，甚至999.999...
     * 3. 若想使熟悉度维持在范围之内，则需要将算法进行优化。
     * - 例如，在 y=sin(x) 的函数图像中，只要在 0≤x≤pi 范围内，其本身就会被限制在 0≤y≤1。
     * - 而当 y<0 时，即使 f(y) 在第二三象限内，但由于 y>=0，因此算法执行时，y 本身就不会小于 0。
     * 4. "熟悉度"函数曲线的含义不一定非得是对碎片的熟悉程度曲线——f(t)随时间的增大而减小。也可以改变它的含义，例如"熟悉度"函数曲线也可以代表着遗忘程度曲线——f(t)随时间的增大而增大。
     * 5. 总结：你想让函数曲线是什么样子就可以是什么样子，你想让函数值为多少就可以是多少，你想让 f(t) 的含义是什么就可以是什么。
     * 甚至，用户可以利用编程知识来编写算法，提供了 if-else/for语句, ??语法糖等。
     */
    @Column(nullable = true)
    var familiarityAlgorithm: String? = null

    /**
     * <在点击按钮时>，评估下一次展示的时间点的算法。
     *
     * 每次展示并点击按钮后，将输入以下变量，计算出下一次展示的时间点.
     * 注意：是 [每次展示并点击按钮 后 ，将输入以下变量]，而非 [每次点击按钮前，或每次展示前，或每次展示后且点击按钮前...]。
     */
    @Column(nullable = true)
    var nextTimeAlgorithm: String? = null

    /**
     * <在刚展示时>，按钮算法
     */
    @Column(nullable = true)
    var buttonAlgorithm: String? = null

    /**
     * 激发算法
     *
     * 在碎片展示时，若满足xxx条件，将展示对应的[激发碎片]，刺激学习者的记忆，以至加深记忆。
     *
     * 例如，若在本次展示前，已经满足连续5次点击了'忘记'按钮，则本次将展示对应的[激发碎片]。
     *
     * 若不存在对应的[激发碎片]，有两种选择或可能性：
     * 1. 将默认使用格外的文字代替，来提醒学习者。（仅仅只能是提醒作用）
     * 2. 对市场上的碎片进行检索，寻找合适的碎片来充当[激发碎片]，
     * 若处于离线状态，将默认使用 1 代替。
     */
    @Column(nullable = true)
    var stimulateAlgorithm: String? = null


    /**
     * 适用群体
     *
     * 例如：大一生、高中生
     */
    @Column(nullable = true)
    var applicableGroups: String? = null


    /**
     * 适用领域
     *
     * 例如：英语、英语-四级英语、语文-高中必备文言文
     */
    @Column(nullable = true)
    var applicableFields: String? = null
}