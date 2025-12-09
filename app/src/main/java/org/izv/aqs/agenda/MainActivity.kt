package org.izv.aqs.agenda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import org.izv.aqs.agenda.ui.compose.MainScreen
import org.izv.aqs.agenda.ui.theme.AgendaTheme
import org.izv.aqs.agenda.ui.viewmodel.AgendaViewModel

class MainActivity : ComponentActivity() {
    private val viewModel: AgendaViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AgendaTheme {
                MainScreen(viewModel)
            }
        }
    }
}