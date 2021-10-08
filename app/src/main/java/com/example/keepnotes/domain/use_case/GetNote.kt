package com.example.keepnotes.domain.use_case

import com.example.keepnotes.domain.model.Note
import com.example.keepnotes.domain.repository.NoteRepository

class GetNote(
    private val repository: NoteRepository
) {
    suspend operator fun invoke(id: Int): Note? {
        return repository.getNotesById(id = id)
    }
}