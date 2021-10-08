package com.example.keepnotes.data.repository

import com.example.keepnotes.data.dao.NoteDao
import com.example.keepnotes.domain.model.Note
import com.example.keepnotes.domain.repository.NoteRepository
import kotlinx.coroutines.flow.Flow

class NoteRepositoryImpl(
    private val dao: NoteDao
) : NoteRepository {
    override fun getNotes(): Flow<List<Note>> {
        return dao.getNotes()
    }

    override suspend fun getNotesById(id: Int): Note? {
        return dao.getNoteById(id = id)
    }

    override suspend fun insertNote(note: Note) {
        return dao.insertNote(note = note)
    }

    override suspend fun deleteNote(note: Note) {
        return dao.deleteNote(note = note)
    }
}