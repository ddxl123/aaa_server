package com.example.demo.share_generator.common

import org.springframework.core.io.support.PathMatchingResourcePatternResolver
import org.springframework.core.io.support.ResourcePatternResolver
import org.springframework.core.type.classreading.CachingMetadataReaderFactory
import org.springframework.core.type.classreading.MetadataReaderFactory
import org.springframework.util.ClassUtils
import kotlin.reflect.KClass

/**
 * 扫描全部类。
 */
fun scanClasses(kotlinPackageName: String): ArrayList<KClass<*>> {
    //spring工具类，可以获取指定路径下的全部类
    val resourcePatternResolver: ResourcePatternResolver = PathMatchingResourcePatternResolver()

    val pattern: String = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
            ClassUtils.convertClassNameToResourcePath(kotlinPackageName) + "/**/*.class"
    // 将会获取全部类。
    val resources = resourcePatternResolver.getResources(pattern)
    // MetadataReader 的工厂类
    val metadataReaderFactory: MetadataReaderFactory = CachingMetadataReaderFactory(resourcePatternResolver)
    val kClasses = arrayListOf<KClass<*>>()
    for (resource in resources) {
        val className = metadataReaderFactory.getMetadataReader(resource).classMetadata.className
        if (className.contains(kotlinPackageName)) {
            kClasses.add(Class.forName(className).kotlin)
        }
    }
    return kClasses
}