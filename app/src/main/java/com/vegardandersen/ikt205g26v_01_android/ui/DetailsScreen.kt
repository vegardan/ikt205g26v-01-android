package com.vegardandersen.ikt205g26v_01_android.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.vegardandersen.ikt205g26v_01_android.model.Note

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(note: Note) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = note.title) },
                colors = TopAppBarDefaults.topAppBarColors()
            )
        }
    ) { innerPadding ->
        Text(
            text = note.description,
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        )
    }
}