package com.vegardandersen.ikt205g26v_01_android.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.vegardandersen.ikt205g26v_01_android.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    notes: List<Note>, onAddClick: () -> Unit, onNoteClick: (Note) -> Unit, onDelete: (Note) -> Unit
) {
    var noteToDelete by remember { mutableStateOf<Note?>(null) }

    Scaffold(topBar = {
        TopAppBar(title = { Text("FastNotes") })
    }, floatingActionButton = {
        FloatingActionButton(onClick = onAddClick) {
            Icon(Icons.Default.Add, contentDescription = "New note")
        }
    }) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding), contentAlignment = Alignment.Center
        ) {
            if (notes.isEmpty()) {
                Text("No notes yet.")
            } else {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    items(notes) { note ->
                        NoteRow(note = note, onClick = { onNoteClick(note) }, onRequestDelete = { noteToDelete = note })
                    }
                }
            }
        }
    }

    if (noteToDelete != null) {
        AlertDialog(
            onDismissRequest = { },
            title = { Text("Delete note?") },
            text = { Text("Delete \"${noteToDelete!!.title}\"?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        onDelete(noteToDelete!!)
                        noteToDelete = null
                    }) {
                    Text("Delete")
                }
            },
            dismissButton = {
                TextButton(
                    onClick = { }) {
                    Text("Cancel")
                }
            })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NoteRow(
    note: Note, onClick: () -> Unit, onRequestDelete: () -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = { value ->
            if (value == SwipeToDismissBoxValue.StartToEnd) {
                onRequestDelete()
            }
            false
        })

    SwipeToDismissBox(
        state = dismissState,
        enableDismissFromStartToEnd = true,
        enableDismissFromEndToStart = false,
        backgroundContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Red)
                    .padding(horizontal = 16.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Row(
                    horizontalArrangement = Arrangement.Start, verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.Delete, contentDescription = "Delete note", tint = Color.White
                    )
                }
            }
        },
        content = {
            ListItem(
                headlineContent = {
                Text(
                    text = note.title, maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            }, supportingContent = {
                Text(
                    text = note.description, maxLines = 1, overflow = TextOverflow.Ellipsis
                )
            }, modifier = Modifier
                    .fillMaxWidth()
                    .clickable(onClick = onClick)
            )
        })
}
