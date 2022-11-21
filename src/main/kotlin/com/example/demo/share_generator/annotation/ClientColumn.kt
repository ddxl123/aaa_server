package com.example.demo.share_generator.annotation

import kotlin.reflect.KClass

enum class NotUseEnum

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
annotation class ClientColumn(val referenceTo: Array<KClass<*>> = [], val useEnum: KClass<*> = NotUseEnum::class)
