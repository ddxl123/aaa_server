
package com.example.demo.entity.unit

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface FragmentTemplatesRepository : JpaRepository<FragmentTemplates, String>, JpaSpecificationExecutor<FragmentTemplates>
