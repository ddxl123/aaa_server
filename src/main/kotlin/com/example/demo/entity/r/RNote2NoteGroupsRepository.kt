
package com.example.demo.entity.r

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface RNote2NoteGroupsRepository : JpaRepository<RNote2NoteGroups, String>, JpaSpecificationExecutor<RNote2NoteGroups>
