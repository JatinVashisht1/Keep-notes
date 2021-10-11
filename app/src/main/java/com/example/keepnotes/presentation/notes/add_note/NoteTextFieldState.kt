package com.example.keepnotes.presentation.notes.add_note

data class NoteTextFieldState (
    val text: String = "",
    val hint: String = "",
    val isHintVisible: Boolean = true
)