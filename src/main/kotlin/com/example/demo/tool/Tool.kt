package com.example.demo.tool


/**
 * RXUserWithName2UserWithInfoS -> r_x_user_with_name2_user_with_info_s
 */
fun String.toLowercaseLine(): String {
    return this.replace(Regex("[A-Z]")) { "_" + it.groups[0]?.value?.lowercase() }.removePrefix("_")
}