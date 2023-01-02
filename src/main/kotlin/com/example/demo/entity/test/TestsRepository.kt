
package com.example.demo.entity.test

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface TestsRepository : JpaRepository<Tests, Long>, JpaSpecificationExecutor<Tests>
