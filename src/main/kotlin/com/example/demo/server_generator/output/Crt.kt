
package com.example.demo.server_generator.output
import com.example.demo.entity.Users
import com.example.demo.entity.info.FragmentMemoryInfos
import com.example.demo.entity.r.RDocument2DocumentGroups
import com.example.demo.entity.r.RFragment2FragmentGroups
import com.example.demo.entity.r.RNote2NoteGroups
import com.example.demo.entity.two_way.ServerSyncInfos
import com.example.demo.entity.unit.Documents
import com.example.demo.entity.unit.FragmentTemplates
import com.example.demo.entity.unit.Fragments
import com.example.demo.entity.unit.MemoryGroups
import com.example.demo.entity.unit.MemoryModels
import com.example.demo.entity.unit.Notes
import com.example.demo.entity.unit_group.DocumentGroups
import com.example.demo.entity.unit_group.FragmentGroups
import com.example.demo.entity.unit_group.NoteGroups
class Crt {
    companion object {

            fun users(
                age: kotlin.Byte,
                email: kotlin.String?,
                password: kotlin.String?,
                phone: kotlin.String?,
                username: kotlin.String,
                createdAt: java.time.Instant,
                updatedAt: java.time.Instant,

            ): Users {
                return Users().also {
                    it.age = age
                    it.email = email
                    it.password = password
                    it.phone = phone
                    it.username = username
                    it.createdAt = createdAt
                    it.updatedAt = updatedAt

                }
            }
        
            fun fragmentMemoryInfos(
                clickTime: kotlin.String?,
                clickValue: kotlin.String?,
                creatorUserId: kotlin.Long,
                currentActualShowTime: kotlin.String?,
                fragmentId: kotlin.String,
                memoryGroupId: kotlin.String,
                nextPlanShowTime: kotlin.String?,
                showFamiliarity: kotlin.String?,
                createdAt: java.time.Instant,
                id: String,
                updatedAt: java.time.Instant,

            ): FragmentMemoryInfos {
                return FragmentMemoryInfos().also {
                    it.clickTime = clickTime
                    it.clickValue = clickValue
                    it.creatorUserId = creatorUserId
                    it.currentActualShowTime = currentActualShowTime
                    it.fragmentId = fragmentId
                    it.memoryGroupId = memoryGroupId
                    it.nextPlanShowTime = nextPlanShowTime
                    it.showFamiliarity = showFamiliarity
                    it.createdAt = createdAt
                    it.id = id
                    it.updatedAt = updatedAt

                }
            }
        
            fun rDocument2DocumentGroups(
                creatorUserId: kotlin.Long,
                documentGroupId: kotlin.String?,
                documentId: kotlin.String,
                createdAt: java.time.Instant,
                id: String,
                updatedAt: java.time.Instant,

            ): RDocument2DocumentGroups {
                return RDocument2DocumentGroups().also {
                    it.creatorUserId = creatorUserId
                    it.documentGroupId = documentGroupId
                    it.documentId = documentId
                    it.createdAt = createdAt
                    it.id = id
                    it.updatedAt = updatedAt

                }
            }
        
            fun rFragment2FragmentGroups(
                creatorUserId: kotlin.Long,
                fragmentGroupId: kotlin.String?,
                fragmentId: kotlin.String,
                createdAt: java.time.Instant,
                id: String,
                updatedAt: java.time.Instant,

            ): RFragment2FragmentGroups {
                return RFragment2FragmentGroups().also {
                    it.creatorUserId = creatorUserId
                    it.fragmentGroupId = fragmentGroupId
                    it.fragmentId = fragmentId
                    it.createdAt = createdAt
                    it.id = id
                    it.updatedAt = updatedAt

                }
            }
        
            fun rNote2NoteGroups(
                creatorUserId: kotlin.Long,
                noteGroupId: kotlin.String?,
                noteId: kotlin.String,
                createdAt: java.time.Instant,
                id: String,
                updatedAt: java.time.Instant,

            ): RNote2NoteGroups {
                return RNote2NoteGroups().also {
                    it.creatorUserId = creatorUserId
                    it.noteGroupId = noteGroupId
                    it.noteId = noteId
                    it.createdAt = createdAt
                    it.id = id
                    it.updatedAt = updatedAt

                }
            }
        
            fun serverSyncInfos(
                recentSyncTime: java.time.Instant,
                userId: kotlin.Long,
                createdAt: java.time.Instant,
                updatedAt: java.time.Instant,

            ): ServerSyncInfos {
                return ServerSyncInfos().also {
                    it.recentSyncTime = recentSyncTime
                    it.userId = userId
                    it.createdAt = createdAt
                    it.updatedAt = updatedAt

                }
            }
        
            fun documents(
                content: kotlin.String,
                creatorUserId: kotlin.Long,
                createdAt: java.time.Instant,
                id: String,
                updatedAt: java.time.Instant,

            ): Documents {
                return Documents().also {
                    it.content = content
                    it.creatorUserId = creatorUserId
                    it.createdAt = createdAt
                    it.id = id
                    it.updatedAt = updatedAt

                }
            }
        
            fun fragmentTemplates(
                content: kotlin.String,
                ownerUserId: kotlin.Long,
                type: com.example.demo.entity.unit.FragmentTemplateType,
                createdAt: java.time.Instant,
                id: String,
                updatedAt: java.time.Instant,

            ): FragmentTemplates {
                return FragmentTemplates().also {
                    it.content = content
                    it.ownerUserId = ownerUserId
                    it.type = type
                    it.createdAt = createdAt
                    it.id = id
                    it.updatedAt = updatedAt

                }
            }
        
            fun fragments(
                content: kotlin.String,
                creatorUserId: kotlin.Long,
                fatherFragmentId: kotlin.String?,
                fragmentTemplateId: kotlin.String?,
                noteId: kotlin.String?,
                title: kotlin.String,
                createdAt: java.time.Instant,
                id: String,
                updatedAt: java.time.Instant,

            ): Fragments {
                return Fragments().also {
                    it.content = content
                    it.creatorUserId = creatorUserId
                    it.fatherFragmentId = fatherFragmentId
                    it.fragmentTemplateId = fragmentTemplateId
                    it.noteId = noteId
                    it.title = title
                    it.createdAt = createdAt
                    it.id = id
                    it.updatedAt = updatedAt

                }
            }
        
            fun memoryGroups(
                creatorUserId: kotlin.Long,
                memoryModelId: kotlin.String?,
                newDisplayOrder: com.example.demo.entity.unit.NewDisplayOrder,
                newReviewDisplayOrder: com.example.demo.entity.unit.NewReviewDisplayOrder,
                reviewInterval: java.time.Instant,
                startTime: java.time.Instant?,
                title: kotlin.String,
                willNewLearnCount: kotlin.Int,
                createdAt: java.time.Instant,
                id: String,
                updatedAt: java.time.Instant,

            ): MemoryGroups {
                return MemoryGroups().also {
                    it.creatorUserId = creatorUserId
                    it.memoryModelId = memoryModelId
                    it.newDisplayOrder = newDisplayOrder
                    it.newReviewDisplayOrder = newReviewDisplayOrder
                    it.reviewInterval = reviewInterval
                    it.startTime = startTime
                    it.title = title
                    it.willNewLearnCount = willNewLearnCount
                    it.createdAt = createdAt
                    it.id = id
                    it.updatedAt = updatedAt

                }
            }
        
            fun memoryModels(
                buttonAlgorithm: kotlin.String,
                creatorUserId: kotlin.Long,
                familiarityAlgorithm: kotlin.String,
                fatherMemoryModelId: kotlin.String?,
                nextTimeAlgorithm: kotlin.String,
                title: kotlin.String,
                createdAt: java.time.Instant,
                id: String,
                updatedAt: java.time.Instant,

            ): MemoryModels {
                return MemoryModels().also {
                    it.buttonAlgorithm = buttonAlgorithm
                    it.creatorUserId = creatorUserId
                    it.familiarityAlgorithm = familiarityAlgorithm
                    it.fatherMemoryModelId = fatherMemoryModelId
                    it.nextTimeAlgorithm = nextTimeAlgorithm
                    it.title = title
                    it.createdAt = createdAt
                    it.id = id
                    it.updatedAt = updatedAt

                }
            }
        
            fun notes(
                content: kotlin.String,
                creatorUserId: kotlin.Long,
                documentId: kotlin.String?,
                fatherNoteId: kotlin.String?,
                createdAt: java.time.Instant,
                id: String,
                updatedAt: java.time.Instant,

            ): Notes {
                return Notes().also {
                    it.content = content
                    it.creatorUserId = creatorUserId
                    it.documentId = documentId
                    it.fatherNoteId = fatherNoteId
                    it.createdAt = createdAt
                    it.id = id
                    it.updatedAt = updatedAt

                }
            }
        
            fun documentGroups(
                creatorUserId: kotlin.Long,
                fatherDocumentGroupsId: kotlin.String?,
                title: kotlin.String,
                createdAt: java.time.Instant,
                id: String,
                updatedAt: java.time.Instant,

            ): DocumentGroups {
                return DocumentGroups().also {
                    it.creatorUserId = creatorUserId
                    it.fatherDocumentGroupsId = fatherDocumentGroupsId
                    it.title = title
                    it.createdAt = createdAt
                    it.id = id
                    it.updatedAt = updatedAt

                }
            }
        
            fun fragmentGroups(
                creatorUserId: kotlin.Long,
                fatherFragmentGroupsId: kotlin.String?,
                title: kotlin.String,
                createdAt: java.time.Instant,
                id: String,
                updatedAt: java.time.Instant,

            ): FragmentGroups {
                return FragmentGroups().also {
                    it.creatorUserId = creatorUserId
                    it.fatherFragmentGroupsId = fatherFragmentGroupsId
                    it.title = title
                    it.createdAt = createdAt
                    it.id = id
                    it.updatedAt = updatedAt

                }
            }
        
            fun noteGroups(
                creatorUserId: kotlin.Long,
                fatherNoteGroupsId: kotlin.String?,
                title: kotlin.String,
                createdAt: java.time.Instant,
                id: String,
                updatedAt: java.time.Instant,

            ): NoteGroups {
                return NoteGroups().also {
                    it.creatorUserId = creatorUserId
                    it.fatherNoteGroupsId = fatherNoteGroupsId
                    it.title = title
                    it.createdAt = createdAt
                    it.id = id
                    it.updatedAt = updatedAt

                }
            }
        
    }
}
