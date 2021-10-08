package com.example.keepnotes.domain.use_case

import com.example.keepnotes.domain.model.Note
import com.example.keepnotes.domain.repository.NoteRepository

class DeleteNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(note: Note){
        repository.deleteNote(note = note)
    }
}