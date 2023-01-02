
package com.example.demo.entity.r

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface RFragment2FragmentGroupsRepository : JpaRepository<RFragment2FragmentGroups, String>, JpaSpecificationExecutor<RFragment2FragmentGroups>
