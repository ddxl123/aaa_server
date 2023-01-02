package com.example.demo.services

import com.example.demo.context
import com.example.demo.entity.UsersRepository
import org.springframework.context.ApplicationContext
import org.springframework.context.ApplicationContextAware
import org.springframework.context.annotation.Lazy

@org.springframework.stereotype.Service
class SimpleRepository(
        val usersRepository: com.example.demo.entity.A,
//        val fragmentMemoryInfosRepository: com.example.demo.entity.info.FragmentMemoryInfosRepository,
//        val rDocument2DocumentGroupsRepository: com.example.demo.entity.r.RDocument2DocumentGroupsRepository,
//        val rFragment2FragmentGroupsRepository: com.example.demo.entity.r.RFragment2FragmentGroupsRepository,
//        val rNote2NoteGroupsRepository: com.example.demo.entity.r.RNote2NoteGroupsRepository,
//        val test2sRepository: com.example.demo.entity.test.Test2sRepository,
//        val testsRepository: com.example.demo.entity.test.TestsRepository,
//        val serverSyncInfosRepository: com.example.demo.entity.two_way.ServerSyncInfosRepository,
//        val documentsRepository: com.example.demo.entity.unit.DocumentsRepository,
//        val fragmentTemplatesRepository: com.example.demo.entity.unit.FragmentTemplatesRepository,
//        val fragmentsRepository: com.example.demo.entity.unit.FragmentsRepository,
//        val memoryGroupsRepository: com.example.demo.entity.unit.MemoryGroupsRepository,
//        val memoryModelsRepository: com.example.demo.entity.unit.MemoryModelsRepository,
//        val notesRepository: com.example.demo.entity.unit.NotesRepository,
//        val documentGroupsRepository: com.example.demo.entity.unit_group.DocumentGroupsRepository,
//        val fragmentGroupsRepository: com.example.demo.entity.unit_group.FragmentGroupsRepository,
//        val noteGroupsRepository: com.example.demo.entity.unit_group.NoteGroupsRepository,
//        val simpleRepository: com.example.demo.services.SimpleRepository,
) {
}