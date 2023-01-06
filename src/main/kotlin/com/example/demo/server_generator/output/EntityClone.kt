package com.example.demo.server_generator.output

class UsersClone(
        var age: kotlin.Byte?,
        var email: kotlin.String?,
        var password: kotlin.String?,
        var phone: kotlin.String?,
        var username: kotlin.String,
        var createdAt: java.time.Instant,
        var id: kotlin.Long,
        var updatedAt: java.time.Instant,

        ) {
    fun toEntity(): com.example.demo.entity.Users {
        return com.example.demo.entity.Users().also {
            it.age = age
            it.email = email
            it.password = password
            it.phone = phone
            it.username = username
            it.createdAt = createdAt
            it.id = id
            it.updatedAt = updatedAt

        }
    }
}

fun com.example.demo.entity.Users.toClone(): UsersClone {
    return UsersClone(
            age = this.age,
            email = this.email,
            password = this.password,
            phone = this.phone,
            username = this.username!!,
            createdAt = this.createdAt!!,
            id = this.id!!,
            updatedAt = this.updatedAt!!,

            )
}

class FragmentMemoryInfosClone(
        var clickTime: kotlin.String?,
        var clickValue: kotlin.String?,
        var creatorUserId: kotlin.Long,
        var currentActualShowTime: kotlin.String?,
        var fragmentId: kotlin.String,
        var memoryGroupId: kotlin.String,
        var nextPlanShowTime: kotlin.String?,
        var showFamiliarity: kotlin.String?,
        var createdAt: java.time.Instant,
        var id: kotlin.String,
        var updatedAt: java.time.Instant,

        ) {
    fun toEntity(): com.example.demo.entity.info.FragmentMemoryInfos {
        return com.example.demo.entity.info.FragmentMemoryInfos().also {
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
}

fun com.example.demo.entity.info.FragmentMemoryInfos.toClone(): FragmentMemoryInfosClone {
    return FragmentMemoryInfosClone(
            clickTime = this.clickTime,
            clickValue = this.clickValue,
            creatorUserId = this.creatorUserId!!,
            currentActualShowTime = this.currentActualShowTime,
            fragmentId = this.fragmentId!!,
            memoryGroupId = this.memoryGroupId!!,
            nextPlanShowTime = this.nextPlanShowTime,
            showFamiliarity = this.showFamiliarity,
            createdAt = this.createdAt!!,
            id = this.id!!,
            updatedAt = this.updatedAt!!,

            )
}

class RDocument2DocumentGroupsClone(
        var creatorUserId: kotlin.Long,
        var documentGroupId: kotlin.String?,
        var documentId: kotlin.String,
        var createdAt: java.time.Instant,
        var id: kotlin.String,
        var updatedAt: java.time.Instant,

        ) {
    fun toEntity(): com.example.demo.entity.r.RDocument2DocumentGroups {
        return com.example.demo.entity.r.RDocument2DocumentGroups().also {
            it.creatorUserId = creatorUserId
            it.documentGroupId = documentGroupId
            it.documentId = documentId
            it.createdAt = createdAt
            it.id = id
            it.updatedAt = updatedAt

        }
    }
}

fun com.example.demo.entity.r.RDocument2DocumentGroups.toClone(): RDocument2DocumentGroupsClone {
    return RDocument2DocumentGroupsClone(
            creatorUserId = this.creatorUserId!!,
            documentGroupId = this.documentGroupId,
            documentId = this.documentId!!,
            createdAt = this.createdAt!!,
            id = this.id!!,
            updatedAt = this.updatedAt!!,

            )
}

class RFragment2FragmentGroupsClone(
        var creatorUserId: kotlin.Long,
        var fragmentGroupId: kotlin.String?,
        var fragmentId: kotlin.String,
        var createdAt: java.time.Instant,
        var id: kotlin.String,
        var updatedAt: java.time.Instant,

        ) {
    fun toEntity(): com.example.demo.entity.r.RFragment2FragmentGroups {
        return com.example.demo.entity.r.RFragment2FragmentGroups().also {
            it.creatorUserId = creatorUserId
            it.fragmentGroupId = fragmentGroupId
            it.fragmentId = fragmentId
            it.createdAt = createdAt
            it.id = id
            it.updatedAt = updatedAt

        }
    }
}

fun com.example.demo.entity.r.RFragment2FragmentGroups.toClone(): RFragment2FragmentGroupsClone {
    return RFragment2FragmentGroupsClone(
            creatorUserId = this.creatorUserId!!,
            fragmentGroupId = this.fragmentGroupId,
            fragmentId = this.fragmentId!!,
            createdAt = this.createdAt!!,
            id = this.id!!,
            updatedAt = this.updatedAt!!,

            )
}

class RNote2NoteGroupsClone(
        var creatorUserId: kotlin.Long,
        var noteGroupId: kotlin.String?,
        var noteId: kotlin.String,
        var createdAt: java.time.Instant,
        var id: kotlin.String,
        var updatedAt: java.time.Instant,

        ) {
    fun toEntity(): com.example.demo.entity.r.RNote2NoteGroups {
        return com.example.demo.entity.r.RNote2NoteGroups().also {
            it.creatorUserId = creatorUserId
            it.noteGroupId = noteGroupId
            it.noteId = noteId
            it.createdAt = createdAt
            it.id = id
            it.updatedAt = updatedAt

        }
    }
}

fun com.example.demo.entity.r.RNote2NoteGroups.toClone(): RNote2NoteGroupsClone {
    return RNote2NoteGroupsClone(
            creatorUserId = this.creatorUserId!!,
            noteGroupId = this.noteGroupId,
            noteId = this.noteId!!,
            createdAt = this.createdAt!!,
            id = this.id!!,
            updatedAt = this.updatedAt!!,

            )
}

class Test2sClone(
        var client_content: kotlin.String,
        var createdAt: java.time.Instant,
        var id: kotlin.Long,
        var updatedAt: java.time.Instant,

        ) {
    fun toEntity(): com.example.demo.entity.test.Test2s {
        return com.example.demo.entity.test.Test2s().also {
            it.client_content = client_content
            it.createdAt = createdAt
            it.id = id
            it.updatedAt = updatedAt

        }
    }
}

fun com.example.demo.entity.test.Test2s.toClone(): Test2sClone {
    return Test2sClone(
            client_content = this.client_content,
            createdAt = this.createdAt!!,
            id = this.id!!,
            updatedAt = this.updatedAt!!,

            )
}

class TestsClone(
        var client_content: kotlin.String,
        var createdAt: java.time.Instant,
        var id: kotlin.Long,
        var updatedAt: java.time.Instant,

        ) {
    fun toEntity(): com.example.demo.entity.test.Tests {
        return com.example.demo.entity.test.Tests().also {
            it.client_content = client_content
            it.createdAt = createdAt
            it.id = id
            it.updatedAt = updatedAt

        }
    }
}

fun com.example.demo.entity.test.Tests.toClone(): TestsClone {
    return TestsClone(
            client_content = this.client_content,
            createdAt = this.createdAt!!,
            id = this.id!!,
            updatedAt = this.updatedAt!!,

            )
}

class DocumentsClone(
        var content: kotlin.String,
        var creatorUserId: kotlin.Long,
        var createdAt: java.time.Instant,
        var id: kotlin.String,
        var updatedAt: java.time.Instant,

        ) {
    fun toEntity(): com.example.demo.entity.unit.Documents {
        return com.example.demo.entity.unit.Documents().also {
            it.content = content
            it.creatorUserId = creatorUserId
            it.createdAt = createdAt
            it.id = id
            it.updatedAt = updatedAt

        }
    }
}

fun com.example.demo.entity.unit.Documents.toClone(): DocumentsClone {
    return DocumentsClone(
            content = this.content!!,
            creatorUserId = this.creatorUserId!!,
            createdAt = this.createdAt!!,
            id = this.id!!,
            updatedAt = this.updatedAt!!,

            )
}

class FragmentTemplatesClone(
        var content: kotlin.String,
        var ownerUserId: kotlin.Long,
        var type: com.example.demo.entity.unit.FragmentTemplateType,
        var createdAt: java.time.Instant,
        var id: kotlin.String,
        var updatedAt: java.time.Instant,

        ) {
    fun toEntity(): com.example.demo.entity.unit.FragmentTemplates {
        return com.example.demo.entity.unit.FragmentTemplates().also {
            it.content = content
            it.ownerUserId = ownerUserId
            it.type = type
            it.createdAt = createdAt
            it.id = id
            it.updatedAt = updatedAt

        }
    }
}

fun com.example.demo.entity.unit.FragmentTemplates.toClone(): FragmentTemplatesClone {
    return FragmentTemplatesClone(
            content = this.content!!,
            ownerUserId = this.ownerUserId!!,
            type = this.type!!,
            createdAt = this.createdAt!!,
            id = this.id!!,
            updatedAt = this.updatedAt!!,

            )
}

class FragmentsClone(
        var client_be_Selected: kotlin.Boolean,
        var content: kotlin.String,
        var creatorUserId: kotlin.Long,
        var fatherFragmentId: kotlin.String?,
        var fragmentTemplateId: kotlin.String?,
        var noteId: kotlin.String?,
        var title: kotlin.String,
        var createdAt: java.time.Instant,
        var id: kotlin.String,
        var updatedAt: java.time.Instant,

        ) {
    fun toEntity(): com.example.demo.entity.unit.Fragments {
        return com.example.demo.entity.unit.Fragments().also {
            it.client_be_Selected = client_be_Selected
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
}

fun com.example.demo.entity.unit.Fragments.toClone(): FragmentsClone {
    return FragmentsClone(
            client_be_Selected = this.client_be_Selected,
            content = this.content!!,
            creatorUserId = this.creatorUserId!!,
            fatherFragmentId = this.fatherFragmentId,
            fragmentTemplateId = this.fragmentTemplateId,
            noteId = this.noteId,
            title = this.title!!,
            createdAt = this.createdAt!!,
            id = this.id!!,
            updatedAt = this.updatedAt!!,

            )
}

class MemoryGroupsClone(
        var creatorUserId: kotlin.Long,
        var memoryModelId: kotlin.String?,
        var newDisplayOrder: com.example.demo.entity.unit.NewDisplayOrder,
        var newReviewDisplayOrder: com.example.demo.entity.unit.NewReviewDisplayOrder,
        var reviewInterval: java.time.Instant,
        var startTime: java.time.Instant?,
        var title: kotlin.String,
        var willNewLearnCount: kotlin.Int,
        var createdAt: java.time.Instant,
        var id: kotlin.String,
        var updatedAt: java.time.Instant,

        ) {
    fun toEntity(): com.example.demo.entity.unit.MemoryGroups {
        return com.example.demo.entity.unit.MemoryGroups().also {
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
}

fun com.example.demo.entity.unit.MemoryGroups.toClone(): MemoryGroupsClone {
    return MemoryGroupsClone(
            creatorUserId = this.creatorUserId!!,
            memoryModelId = this.memoryModelId,
            newDisplayOrder = this.newDisplayOrder!!,
            newReviewDisplayOrder = this.newReviewDisplayOrder!!,
            reviewInterval = this.reviewInterval!!,
            startTime = this.startTime,
            title = this.title!!,
            willNewLearnCount = this.willNewLearnCount!!,
            createdAt = this.createdAt!!,
            id = this.id!!,
            updatedAt = this.updatedAt!!,

            )
}

class MemoryModelsClone(
        var buttonAlgorithm: kotlin.String,
        var creatorUserId: kotlin.Long,
        var familiarityAlgorithm: kotlin.String,
        var fatherMemoryModelId: kotlin.String?,
        var nextTimeAlgorithm: kotlin.String,
        var title: kotlin.String,
        var createdAt: java.time.Instant,
        var id: kotlin.String,
        var updatedAt: java.time.Instant,

        ) {
    fun toEntity(): com.example.demo.entity.unit.MemoryModels {
        return com.example.demo.entity.unit.MemoryModels().also {
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
}

fun com.example.demo.entity.unit.MemoryModels.toClone(): MemoryModelsClone {
    return MemoryModelsClone(
            buttonAlgorithm = this.buttonAlgorithm!!,
            creatorUserId = this.creatorUserId!!,
            familiarityAlgorithm = this.familiarityAlgorithm!!,
            fatherMemoryModelId = this.fatherMemoryModelId,
            nextTimeAlgorithm = this.nextTimeAlgorithm!!,
            title = this.title!!,
            createdAt = this.createdAt!!,
            id = this.id!!,
            updatedAt = this.updatedAt!!,

            )
}

class NotesClone(
        var content: kotlin.String,
        var creatorUserId: kotlin.Long,
        var documentId: kotlin.String?,
        var fatherNoteId: kotlin.String?,
        var createdAt: java.time.Instant,
        var id: kotlin.String,
        var updatedAt: java.time.Instant,

        ) {
    fun toEntity(): com.example.demo.entity.unit.Notes {
        return com.example.demo.entity.unit.Notes().also {
            it.content = content
            it.creatorUserId = creatorUserId
            it.documentId = documentId
            it.fatherNoteId = fatherNoteId
            it.createdAt = createdAt
            it.id = id
            it.updatedAt = updatedAt

        }
    }
}

fun com.example.demo.entity.unit.Notes.toClone(): NotesClone {
    return NotesClone(
            content = this.content!!,
            creatorUserId = this.creatorUserId!!,
            documentId = this.documentId,
            fatherNoteId = this.fatherNoteId,
            createdAt = this.createdAt!!,
            id = this.id!!,
            updatedAt = this.updatedAt!!,

            )
}

class DocumentGroupsClone(
        var creatorUserId: kotlin.Long,
        var fatherDocumentGroupsId: kotlin.String?,
        var title: kotlin.String,
        var createdAt: java.time.Instant,
        var id: kotlin.String,
        var updatedAt: java.time.Instant,

        ) {
    fun toEntity(): com.example.demo.entity.unit_group.DocumentGroups {
        return com.example.demo.entity.unit_group.DocumentGroups().also {
            it.creatorUserId = creatorUserId
            it.fatherDocumentGroupsId = fatherDocumentGroupsId
            it.title = title
            it.createdAt = createdAt
            it.id = id
            it.updatedAt = updatedAt

        }
    }
}

fun com.example.demo.entity.unit_group.DocumentGroups.toClone(): DocumentGroupsClone {
    return DocumentGroupsClone(
            creatorUserId = this.creatorUserId!!,
            fatherDocumentGroupsId = this.fatherDocumentGroupsId,
            title = this.title!!,
            createdAt = this.createdAt!!,
            id = this.id!!,
            updatedAt = this.updatedAt!!,

            )
}

class FragmentGroupsClone(
        var client_be_Selected: kotlin.Boolean,
        var creatorUserId: kotlin.Long,
        var fatherFragmentGroupsId: kotlin.String?,
        var title: kotlin.String,
        var createdAt: java.time.Instant,
        var id: kotlin.String,
        var updatedAt: java.time.Instant,

        ) {
    fun toEntity(): com.example.demo.entity.unit_group.FragmentGroups {
        return com.example.demo.entity.unit_group.FragmentGroups().also {
            it.client_be_Selected = client_be_Selected
            it.creatorUserId = creatorUserId
            it.fatherFragmentGroupsId = fatherFragmentGroupsId
            it.title = title
            it.createdAt = createdAt
            it.id = id
            it.updatedAt = updatedAt

        }
    }
}

fun com.example.demo.entity.unit_group.FragmentGroups.toClone(): FragmentGroupsClone {
    return FragmentGroupsClone(
            client_be_Selected = this.client_be_Selected,
            creatorUserId = this.creatorUserId!!,
            fatherFragmentGroupsId = this.fatherFragmentGroupsId,
            title = this.title!!,
            createdAt = this.createdAt!!,
            id = this.id!!,
            updatedAt = this.updatedAt!!,

            )
}

class NoteGroupsClone(
        var creatorUserId: kotlin.Long,
        var fatherNoteGroupsId: kotlin.String?,
        var title: kotlin.String,
        var createdAt: java.time.Instant,
        var id: kotlin.String,
        var updatedAt: java.time.Instant,

        ) {
    fun toEntity(): com.example.demo.entity.unit_group.NoteGroups {
        return com.example.demo.entity.unit_group.NoteGroups().also {
            it.creatorUserId = creatorUserId
            it.fatherNoteGroupsId = fatherNoteGroupsId
            it.title = title
            it.createdAt = createdAt
            it.id = id
            it.updatedAt = updatedAt

        }
    }
}

fun com.example.demo.entity.unit_group.NoteGroups.toClone(): NoteGroupsClone {
    return NoteGroupsClone(
            creatorUserId = this.creatorUserId!!,
            fatherNoteGroupsId = this.fatherNoteGroupsId,
            title = this.title!!,
            createdAt = this.createdAt!!,
            id = this.id!!,
            updatedAt = this.updatedAt!!,

            )
}
