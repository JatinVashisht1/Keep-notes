package com.example.keepnotes.domain.use_case

import android.util.Log
//import com.example.keepnotes.domain.model.InvalidNoteException
import com.example.keepnotes.domain.model.Note
import com.example.keepnotes.domain.repository.NoteRepository
import kotlin.jvm.Throws

class AddNote(val repository: NoteRepository) {
//    @Throws(InvalidNoteException::class)
    suspend operator fun invoke(note: Note){
        if(note.content.isBlank()){
//            throw InvalidNoteException("The content of title cannot be empty")
            Log.d("HomeScreen", "Exception")
        }
        return repository.insertNote(note = note)
    }
}