package com.vegardandersen.ikt205g26v_01_android

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vegardandersen.ikt205g26v_01_android.ui.DetailsScreen
import com.vegardandersen.ikt205g26v_01_android.ui.HomeScreen
import com.vegardandersen.ikt205g26v_01_android.ui.NewNoteScreen
import com.vegardandersen.ikt205g26v_01_android.viewmodel.NoteViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val viewModel: NoteViewModel = viewModel()

            Surface(color = MaterialTheme.colorScheme.background) {
                NavHost(navController = navController, startDestination = "home") {
                    composable("home") {
                        HomeScreen(
                            notes = viewModel.notes,
                            onAddClick = { navController.navigate("new_note") },
                            onNoteClick = { note ->
                                val index = viewModel.notes.indexOf(note)
                                navController.navigate("details/$index")
                            },
                            onDelete = { note ->
                                val index = viewModel.notes.indexOf(note)
                                viewModel.deleteNote(index)
                            })
                    }

                    composable("new_note") {
                        NewNoteScreen(
                            onSave = { title, description ->
                                viewModel.addNote(title, description)
                                navController.popBackStack()
                            }, onBackClick = { navController.popBackStack() })
                    }

                    composable("details/{index}") { backStackEntry ->
                        val index = backStackEntry.arguments?.getString("index")?.toInt() ?: 0

                        val note = viewModel.notes.getOrNull(index)

                        if (note != null) {
                            DetailsScreen(note = note, onBackClick = { navController.popBackStack() })
                        }
                    }
                }
            }
        }
    }
}
