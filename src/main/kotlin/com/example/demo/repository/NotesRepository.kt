package com.example.demo.repository;

import com.example.demo.entity.unit.Notes
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface NotesRepository : JpaRepository<Notes, Long>, JpaSpecificationExecutor<Notes> {
}