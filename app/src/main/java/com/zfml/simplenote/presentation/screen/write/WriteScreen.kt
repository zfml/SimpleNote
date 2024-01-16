package com.zfml.simplenote.presentation.screen.write

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.zfml.simplenote.R
import com.zfml.simplenote.presentation.components.DisplayAlertDialog
import com.zfml.simplenote.presentation.components.TransparentTextField

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WriteScreen(
    navigateToHome: () -> Unit,
) {

    val viewModel: WriteViewModel = hiltViewModel()

    val titleState by viewModel.titleState
    val descriptionState by viewModel.descriptionState
    val context = LocalContext.current

    var openDeleteDialog by remember { mutableStateOf(false) }


    if (openDeleteDialog) {
        DisplayAlertDialog(
            title = "Delete Note?",
            description = "Are you sure you want to delete?",
            openDialog = openDeleteDialog,
            onClosedDialog = { openDeleteDialog = false },
            onConfirmClicked = {
                viewModel.onEvent(WriteEvent.DeleteNote)
                navigateToHome()
                openDeleteDialog = false
            },
            onDismissClicked = { openDeleteDialog = false }
        )
    }


    Scaffold(
        topBar = {

            TopAppBar(
                title = {
                    Text(
                        text = ""
                    )
                },
                navigationIcon = {
                    IconButton(onClick = navigateToHome) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back Icon"
                        )
                    }
                },
                actions = {
                    if (viewModel.deletable.value) {
                        IconButton(onClick = { openDeleteDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete Icon"
                            )
                        }
                    }

                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                if (viewModel.IsInvalidInput()) {
                    viewModel.onEvent(WriteEvent.SaveNote)
                    Toast.makeText(
                        context,
                        "Successfully Added",
                        Toast.LENGTH_SHORT
                    ).show()
                    navigateToHome()
                } else {
                    Toast.makeText(
                        context,
                        "Title or Description can't be  Empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_save_24),
                    contentDescription = "Delete Icon"
                )
            }
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(14.dp)
            ) {
                TransparentTextField(
                    modifier = Modifier.fillMaxWidth(),
                    text = titleState.text,
                    hint = titleState.hint,
                    isHintVisible = titleState.isHintVisible,
                    onValueChanged = {
                        viewModel.onEvent(WriteEvent.ChangeTitle(it))
                    },
                    onFocusChanged = {
                        viewModel.onEvent(WriteEvent.ChangeTitleFocus(it))
                    },
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleMedium.fontSize
                    )
                )
                Spacer(modifier = Modifier.height(14.dp))
                Surface(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(2.dp)
                ) {}
                TransparentTextField(
                    modifier = Modifier.fillMaxHeight(),
                    text = descriptionState.text,
                    hint = descriptionState.hint,
                    isHintVisible = descriptionState.isHintVisible,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize
                    ),
                    onValueChanged = {
                        viewModel.onEvent(WriteEvent.ChangeDescription(it))
                    },
                    onFocusChanged = {
                        viewModel.onEvent(WriteEvent.ChangeDescriptionFocus(it))
                    },

                    )

            }
        }
    )

}