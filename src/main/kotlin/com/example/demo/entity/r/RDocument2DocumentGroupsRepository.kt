
package com.example.demo.entity.r

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface RDocument2DocumentGroupsRepository : JpaRepository<RDocument2DocumentGroups, String>, JpaSpecificationExecutor<RDocument2DocumentGroups>
