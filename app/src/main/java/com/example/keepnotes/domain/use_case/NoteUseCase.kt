package com.example.keepnotes.domain.use_case

data class NoteUseCase (
    val addNote: AddNote,
    val getNote: GetNote,
    val getNotes: GetNotes,
    val deleteNote: DeleteNote
)