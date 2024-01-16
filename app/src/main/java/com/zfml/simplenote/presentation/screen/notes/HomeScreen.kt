package com.zfml.simplenote.presentation.screen.notes

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zfml.simplenote.domain.model.Note
import com.zfml.simplenote.domain.util.toReadableDate

@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToWriteScreen: () -> Unit,
    navigateToWriteScreenWithArg: (Int) -> Unit,
) {
    val viewModel: HomeViewModel = hiltViewModel()
    val notesUiState by viewModel.noteUiState.collectAsStateWithLifecycle()

    val searchUiState by viewModel.searchState
    val focusManger = LocalFocusManager.current

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "Notes",
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleLarge.fontSize
                    )
                )
            })
        },
        floatingActionButton = {
            FloatingActionButton(onClick = navigateToWriteScreen) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Icon")
            }
        },
        content = { padding ->


            when (val state = notesUiState) {
                is NotesUiState.Error -> {
                    EmptyPage(
                        title = state.exception.toString()
                    )
                }

                is NotesUiState.Success -> {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(padding)
                    ) {
                        OutlinedTextField(
                            modifier = Modifier
                                .onFocusChanged {
                                    viewModel.onFocusSearch(it)
                                }
                                .fillMaxWidth()
                                .padding(8.dp),
                            value = searchUiState.searchQuery,
                            onValueChange = {
                                viewModel.onSearchQueryChange(it)
                            },
                            placeholder = {
                                Text(
                                    text = "Search your notes"
                                )
                            },
                            leadingIcon = {
                                Icon(
                                    imageVector = Icons.Default.Search,
                                    contentDescription = "Search Icon"
                                )
                            },
                            trailingIcon = {
                                if (searchUiState.isFocus) {
                                    IconButton(onClick = {
                                        viewModel.closeSearch()
                                        focusManger.clearFocus()
                                        viewModel.onSearchQueryChange("")
                                    }
                                    ) {
                                        Icon(
                                            imageVector = Icons.Default.Close,
                                            contentDescription = "Close Icon"
                                        )
                                    }
                                }

                            }
                        )


                        HomeContent(
                            notes = state.notes,
                            onClick = navigateToWriteScreenWithArg
                        )

                    }

                }
            }
        }
    )
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun HomeContent(
    notes: List<Note>,
    onClick: (Int) -> Unit,
) {
    if (notes.isEmpty()) {
        EmptyPage()
    } else {
        Column(
            modifier = Modifier
        ) {
            LazyColumn(
            ) {
                items(items = notes) { note ->
                    NoteHolder(note = note, onClick = onClick)
                }
            }
        }

    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoteHolder(
    modifier: Modifier = Modifier,
    note: Note,
    onClick: (Int) -> Unit,
) {

    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
            .clickable {
                note.id?.let { onClick(it) }
            },
        tonalElevation = 1.dp

    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = note.title,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize,
                    ),
                    maxLines = 1
                )
                Text(
                    text = note.date.toReadableDate(),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        fontWeight = FontWeight.Light
                    ),
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = note.description,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.bodyMedium.fontSize
                ),
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }


}

@Composable
fun EmptyPage(
    title: String = "No Note ",
    subTitle: String = "",
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = title,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleMedium.fontSize
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = subTitle
            )
        }
    }
}