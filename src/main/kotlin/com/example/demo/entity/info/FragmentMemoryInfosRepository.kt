
package com.example.demo.entity.info

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface FragmentMemoryInfosRepository : JpaRepository<FragmentMemoryInfos, String>, JpaSpecificationExecutor<FragmentMemoryInfos>
