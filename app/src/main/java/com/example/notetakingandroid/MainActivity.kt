package com.example.notetakingandroid

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.defaultDecayAnimationSpec
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.notetakingandroid.feature_note.presentation.navigation.Screen
import com.example.notetakingandroid.ui.theme.NoteTakingAndroidTheme
import dagger.hilt.android.AndroidEntryPoint
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavType
import androidx.navigation.navArgument
import com.example.notetakingandroid.feature_note.presentation.add_edit_note.AddEditNoteScreen
import com.example.notetakingandroid.feature_note.presentation.add_edit_note.AddEditNoteViewModel
import com.example.notetakingandroid.feature_note.presentation.notes.NotesScreen
import com.example.notetakingandroid.feature_note.presentation.notes.NotesViewModel

const val TAG = "MainActivity"

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @ExperimentalAnimationApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NoteTakingAndroidTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {

                    val navController = rememberNavController()

                    NavHost(navController = navController, startDestination = Screen.NotesList.route )
                    {
                        composable(Screen.NotesList.route)
                        {
                            val viewModel = hiltViewModel<NotesViewModel>()
                            NotesScreen(viewModel = viewModel, navController =navController )
                        }

                        composable(
                            route = Screen.NoteAddEdit.route +
                                    "?noteID={noteID}&noteColor={noteColor}",
                            arguments = listOf(
                                navArgument(
                                    name = "noteID"
                                ){
                                    type = NavType.IntType
                                    defaultValue = -1
                                },
                                navArgument(
                                    name ="noteColor"
                                ){
                                    type = NavType.IntType
                                    defaultValue =-1
                                }
                            )
                        )
                        {
                            val color = it.arguments?.getInt("noteColor") ?: -1

                            val viewModel = hiltViewModel<AddEditNoteViewModel>()
                            AddEditNoteScreen(
                                 navController = navController,
                                 viewModel = viewModel,
                                 noteColor = color,
                             )
                        }
                    }

                }
            }
        }
    }
}