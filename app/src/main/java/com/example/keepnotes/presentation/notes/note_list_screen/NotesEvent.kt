package com.example.keepnotes.presentation.notes.note_list_screen

import com.example.keepnotes.domain.model.Note
import com.example.keepnotes.domain.util.NoteOrder

sealed class NotesEvent{
    data class Order(val noteOrder: NoteOrder) : NotesEvent()
    data class DeleteNote(val note: Note) : NotesEvent()
    object RestoreNote : NotesEvent()
    object ToggleOrderSelection: NotesEvent()
}