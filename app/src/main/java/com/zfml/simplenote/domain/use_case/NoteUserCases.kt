package com.zfml.simplenote.domain.use_case

data class NoteUserCases(
    val deleteNote: DeleteNote,
    val getNoteById: GetNoteById,
    val getNotes: GetNotes,
    val searchNote: SearchNote,
    val insertNote: InsertNote
)