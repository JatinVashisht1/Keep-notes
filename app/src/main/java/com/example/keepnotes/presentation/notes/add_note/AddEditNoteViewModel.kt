package com.example.keepnotes.presentation.notes.add_note

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.keepnotes.domain.model.Note
import com.example.keepnotes.domain.use_case.NoteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddEditNoteViewModel @Inject constructor(
    private val noteUseCase: NoteUseCase,
    savedStateHandle: SavedStateHandle,

) : ViewModel() {
    private val _noteTitle = mutableStateOf(NoteTextFieldState(hint = "Title"))
    val noteTitle: State<NoteTextFieldState> = _noteTitle

    private val _content = mutableStateOf(NoteTextFieldState(hint = "Content"))
    val content: State<NoteTextFieldState> = _content

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    var currentNoteId: Int? = null


//    init {
//        savedStateHandle.get<Int>("noteId").let { noteId ->
//            if (noteId != -1) {
//                viewModelScope.launch {
//                    if (noteId != null) {
//                        noteUseCase.getNote(noteId)?.also { note ->
//                            currentNoteId = note.id
//                            _noteTitle.value = noteTitle.value.copy(
//                                text = note.content,
//                                isHintVisible = false
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }

    fun getNote(id: Int){
        viewModelScope.launch {
           val a =  noteUseCase.getNote(id)
//            _noteTitle.value = a.title
        }
    }

    fun onEvent(event: AddEditNoteEvent) {
        when (event) {
            is AddEditNoteEvent.ChangeContentFocus -> {
                _content.value = content.value.copy(
                    isHintVisible = !event.focusState.isFocused && _content.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.EnteredTitle -> {
                _noteTitle.value = noteTitle.value.copy(
                    text = event.title
                )
            }

            is AddEditNoteEvent.EnteredContent -> {
                _content.value = content.value.copy(
                    text = event.content
                )
            }

            is AddEditNoteEvent.ChangeTitleFocus -> {
                _noteTitle.value = noteTitle.value.copy(
                    isHintVisible = !event.focusState.isFocused && noteTitle.value.text.isBlank()
                )
            }

            is AddEditNoteEvent.SaveNote -> {
                viewModelScope.launch {
                    try {
                        noteUseCase.addNote(
                            Note(
                                title = noteTitle.value.text,
                                content = content.value.text,
                                timestamp = System.currentTimeMillis(),
                                id = currentNoteId
                            )
                        )
                        _eventFlow.emit(UiEvent.SaveNote)
                    } catch (e: Exception) {
                        _eventFlow.emit(
                            UiEvent.ShowSnackbar(
                                message = e.toString() ?: "Couldn't save note"
                            )
                        )
                    }
                }
            }
        }
    }

    sealed class UiEvent {
        data class ShowSnackbar(val message: String) : UiEvent()
        object SaveNote : UiEvent()
    }
}