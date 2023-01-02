
package com.example.demo.entity.test

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface Test2sRepository : JpaRepository<Test2s, Long>, JpaSpecificationExecutor<Test2s>
