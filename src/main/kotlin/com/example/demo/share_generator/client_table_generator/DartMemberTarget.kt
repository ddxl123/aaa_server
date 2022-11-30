package com.example.demo.share_generator.client_table_generator

import com.example.demo.share_generator.common.TypeTarget

class DartMemberTarget(
        val name: String,
        val typeTarget: TypeTarget,
        /**
         * 完全基于字段是否为空。
         */
        val isNullable: Boolean,
        val referenceTos: ArrayList<String>,
) {
    override fun toString(): String {
        return "(name: $name, typeTarget: $typeTarget, isNullable: $isNullable, referenceTos: $referenceTos)"
    }
}
