package com.example.demo.entity

import java.time.Instant
import javax.persistence.Entity
import javax.persistence.Id


@Entity
class TestEntity {
    @Id
    var id: Long? = null

    var _byte: Byte? = null
    var _short: Short? = null
    var _int: Int? = null
    var _long: Long? = null
    var _float: Float? = null

    var _char: Char? = null
    var _string: String? = null

    var _instant: Instant? = null
}