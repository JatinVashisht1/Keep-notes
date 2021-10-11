package com.example.keepnotes.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.keepnotes.presentation.notes.add_note.AddNoteScreen
import com.example.keepnotes.presentation.notes.note_list_screen.NoteListScreen
import com.example.keepnotes.presentation.ui.theme.KeepNotesTheme
import com.example.keepnotes.presentation.util.Screen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KeepNotesTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    val navController = rememberNavController()
                    NavHost(
                        navController = navController,
                        startDestination = Screen.NotesScreen.route,
                    ){
                        composable(
                            route = Screen.NotesScreen.route
                        ){
                            NoteListScreen(navController = navController)
                        }
                        composable(
                            Screen.AddEditNoteScreen.route +
                                    "noteId/{noteId}",
                            arguments = listOf(
                                navArgument("noteId"){
                                    type = NavType.IntType
                                    defaultValue = -1
                                }
                            )
                        ){
                            AddNoteScreen(navController = navController, param = it.arguments?.getInt("noteId"))
                        }
                    }
                }
            }
        }
    }
}