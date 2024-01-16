package com.zfml.simplenote.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresApi
import androidx.navigation.compose.rememberNavController
import com.zfml.simplenote.presentation.navigation.Screen
import com.zfml.simplenote.presentation.navigation.SetUpNavGraph
import com.zfml.simplenote.presentation.ui.theme.SimpleNoteTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SimpleNoteTheme {
                val navController = rememberNavController()
               SetUpNavGraph(
                   navController = navController,
                   startDestination = Screen.Home.route
               )
            }
        }
    }
}