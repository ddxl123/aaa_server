
package com.example.demo.entity.unit_group

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface NoteGroupsRepository : JpaRepository<NoteGroups, String>, JpaSpecificationExecutor<NoteGroups>
