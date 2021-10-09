package com.example.keepnotes.presentation.notes.note_list_screen

import com.example.keepnotes.domain.model.Note
import com.example.keepnotes.domain.util.NoteOrder
import com.example.keepnotes.domain.util.OrderType

data class NoteState (
    val notes: List<Note> = emptyList(),
    val isSelectionOrderVisible: Boolean = false,
    val noteOrder: NoteOrder = NoteOrder.Date(OrderType.Descending)
)