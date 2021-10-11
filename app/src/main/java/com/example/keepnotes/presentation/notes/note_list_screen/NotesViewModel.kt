package com.example.keepnotes.presentation.notes.note_list_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keepnotes.domain.model.Note
import com.example.keepnotes.domain.use_case.NoteUseCase
import com.example.keepnotes.domain.util.NoteOrder
import com.example.keepnotes.domain.util.OrderType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NotesViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase
): ViewModel() {
    private val _state = mutableStateOf<NoteState>(NoteState())
    val state: State<NoteState> = _state

    private var getNotesJob : Job? = null
    private var recentlyDeletedNote: Note? = null

    init {
        getNotes(NoteOrder.Date(OrderType.Descending))
    }

    fun onEvent(event: NotesEvent){
//        viewModelScope.launch {
            when (event) {
                is NotesEvent.DeleteNote -> {
                    viewModelScope.launch {
                        noteUseCase.deleteNote(event.note)
                        recentlyDeletedNote = event.note
                    }
                }
                is NotesEvent.Order -> {
                    if (state.value.noteOrder::class == event.noteOrder::class &&
                        state.value.noteOrder.orderType::class == event.noteOrder.orderType::class
                    ) {
                        return
                    }
                    getNotes(event.noteOrder)
                }
                is NotesEvent.RestoreNote -> {
                    viewModelScope.launch {
                        noteUseCase.addNote(recentlyDeletedNote ?: return@launch)
                    }
                }
                is NotesEvent.ToggleOrderSelection -> {
                    _state.value = _state.value.copy(
                        isSelectionOrderVisible = !state.value.isSelectionOrderVisible
                    )
                }
            }
        }
//    }

    private fun getNotes(noteOrder: NoteOrder){
        getNotesJob?.cancel()
        getNotesJob = noteUseCase.getNotes(noteOrder = noteOrder)
        .onEach {notes->
            _state.value = _state.value.copy(
                notes = notes,
                noteOrder = noteOrder
            )
        }.launchIn(viewModelScope)
    }

}