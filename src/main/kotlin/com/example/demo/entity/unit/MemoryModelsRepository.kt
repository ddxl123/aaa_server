
package com.example.demo.entity.unit

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface MemoryModelsRepository : JpaRepository<MemoryModels, String>, JpaSpecificationExecutor<MemoryModels>
