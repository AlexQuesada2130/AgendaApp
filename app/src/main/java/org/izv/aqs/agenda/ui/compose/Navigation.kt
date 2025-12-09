package org.izv.aqs.agenda.ui.compose

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import org.izv.aqs.agenda.ui.viewmodel.AgendaViewModel


@Composable
fun MainScreen(viewModel: AgendaViewModel) {
    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
        Navigation(innerPadding, viewModel)
    }
}

@Composable
fun Navigation(innerPadding : PaddingValues, viewModel: AgendaViewModel) {

    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "home-screen"
    ) {
        composable("home-screen") {
            //MainMenu(navController)
            HomeScreen(navController, innerPadding, viewModel)
        }
        composable("add-contact") {
           // AddContactScreen(navController, contactos)
            AddContact(navController, innerPadding, viewModel)
        }
        composable("edit-contact") {
            //SearchContactScreen(navController, contactos)
            EditContact(navController, innerPadding, viewModel)
        }
    }
}
