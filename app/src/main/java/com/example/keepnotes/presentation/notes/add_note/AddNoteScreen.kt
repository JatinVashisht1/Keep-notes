package com.example.keepnotes.presentation.notes.add_note

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Save
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.keepnotes.presentation.notes.add_note.components.CustomTextField
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddNoteScreen(
    navController: NavController,
    viewModel: AddEditNoteViewModel = hiltViewModel(),
    param: Int?
) {
    val titleState = viewModel.noteTitle.value
    val contentState = viewModel.content.value

    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    viewModel.currentNoteId = param

    LaunchedEffect(key1 = 1) {
        viewModel.eventFlow.collectLatest { event ->
            when (event) {
                is AddEditNoteViewModel.UiEvent.ShowSnackbar -> {
                    scaffoldState.snackbarHostState.showSnackbar(message = event.message)
                }
                is AddEditNoteViewModel.UiEvent.SaveNote -> {
                    navController.navigateUp()
                }
            }

        }
    }
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.onEvent(AddEditNoteEvent.SaveNote) },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Save, contentDescription = "Save note")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),

            ) {

                Spacer(modifier = Modifier.height(16.dp))
                CustomTextField(
                    text = titleState.text,
                    hint = titleState.hint,
                    onValueChange = {
                        viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditNoteEvent.ChangeTitleFocus(it))
                    },
                    isHintVisible = titleState.isHintVisible,
                    singleLine = true,
                    textStyle = MaterialTheme.typography.h5,
//                    modifier = Modifier.fmw
                )
                Spacer(modifier = Modifier.height(16.dp))

                CustomTextField(
                    text = contentState.text,
                    hint = contentState.hint,
                    onValueChange = {
                        viewModel.onEvent(AddEditNoteEvent.EnteredContent(it))
                    },
                    onFocusChange = {
                        viewModel.onEvent(AddEditNoteEvent.ChangeContentFocus(it))
                    },
                    isHintVisible = contentState.isHintVisible,
                    textStyle = MaterialTheme.typography.body1,
                    modifier = Modifier
                )
            }
//        }
    }
//    Column(modifier = Modifier.fillMaxSize().background(color = MaterialTheme.colors.primary)){
//    Text("This is test text", style = MaterialTheme.typography.h6)
//        TextField(value = viewModel.content.toString(), onValueChange = {
//            viewModel.onEvent(AddEditNoteEvent.EnteredTitle(it))
//        }
//        )
//    }
}