package com.example.notetakingandroid.feature_note.presentation.notes

import android.util.Log
import androidx.compose.animation.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Sort
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.notetakingandroid.feature_note.presentation.navigation.Screen
import com.example.notetakingandroid.feature_note.presentation.notes.components.NoteCard
import com.example.notetakingandroid.feature_note.presentation.notes.components.OrderSection
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch

@ExperimentalAnimationApi
@Composable
fun NotesScreen(
    viewModel: NotesViewModel,
    navController: NavController,
) {
    val state = viewModel.state.value
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()


    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                          navController.navigate(Screen.NoteAddEdit.route)
                },
                backgroundColor = MaterialTheme.colors.primary
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "floating add button ")
            }
        },
        scaffoldState = scaffoldState
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ){
                Text(
                    text = "Your Notes",
                    style = MaterialTheme.typography.h5
                )

                IconButton(onClick = {
                    viewModel.onTriggerEvent(NotesEvent.ToggleOrderSection)
                },
                ) {
                    Icon(imageVector = Icons.Default.Sort, contentDescription ="Sort" )
                }
            }
            AnimatedVisibility(
                visible = state.isOrderSectionVisible,
                enter = fadeIn() + slideInVertically(),
                exit = fadeOut() + slideOutVertically(),
            ) {
                OrderSection(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp),
                    noteOrder = state.noteOrder,
                    onOrderChange = {
                        viewModel.onTriggerEvent(NotesEvent.Order(it))
                    }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn(modifier = Modifier.fillMaxSize())
            {
                items(state.notes){ note ->
                    NoteCard(
                        note = note,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable(enabled = true, onClick = {

                                //Navigating to Note Detail Screen,
                                navController.navigate(
                                    Screen.NoteAddEdit.route +
                                            "?noteID=${note.uid}&noteColor=${note.color}"
                                )
                            }),
                        onDeleteClick = {
                          viewModel.onTriggerEvent(NotesEvent.DeleteNote(note.uid))
                          scope.launch {
                              val result = scaffoldState.snackbarHostState.showSnackbar(
                                  message = "Note Deleted",
                                  actionLabel = "Undo"
                              )
                              
                              if(result == SnackbarResult.ActionPerformed){
                                  viewModel.onTriggerEvent(NotesEvent.RestoreNote)
                              }
                          }
                        }
                    )
                    
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}