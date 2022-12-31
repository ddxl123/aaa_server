import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "2.7.5"
    id("io.spring.dependency-management") version "1.0.15.RELEASE"
    kotlin("jvm") version "1.6.21"
    kotlin("plugin.spring") version "1.6.21"
}

group = "com.long"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter")
    // 可以启动服务器
    implementation("org.springframework.boot:spring-boot-starter-web")
    // mysql 驱动
    implementation("mysql:mysql-connector-java")
    // spring-data-jpa
    implementation("org.springframework.boot:spring-boot-starter-data-jpa:2.7.5")
    // 热部署
    implementation("org.springframework.boot:spring-boot-devtools:2.7.5")
    // redis
//    implementation("org.springframework.boot:spring-boot-starter-data-redis")
    // 邮件发送
    implementation("org.springframework.boot:spring-boot-starter-mail")
    // 认证与鉴权
    implementation("cn.dev33:sa-token-spring-boot-starter:1.33.0")
    // jwt
    implementation("com.auth0:java-jwt:4.2.1")
    // sa-token-jwt
    implementation("cn.dev33:sa-token-jwt:1.33.0")

    // 认证与鉴权的redis
//    implementation("cn.dev33:sa-token-dao-redis:1.33.0")

    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.withType<KotlinCompile> {
    kotlinOptions {
        freeCompilerArgs = listOf("-Xjsr305=strict")
        jvmTarget = "17"
    }
}

tasks.withType<Test> {
    useJUnitPlatform()
}
