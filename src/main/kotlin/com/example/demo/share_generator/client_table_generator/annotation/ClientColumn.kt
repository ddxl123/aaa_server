package com.example.demo.share_generator.client_table_generator.annotation

import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ClientColumn(val referenceTo: Array<KClass<*>> = [], val isOnlyClient: Boolean = false)
