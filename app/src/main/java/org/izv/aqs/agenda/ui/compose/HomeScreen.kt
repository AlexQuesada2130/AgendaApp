package org.izv.aqs.agenda.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.izv.aqs.agenda.ui.viewmodel.AgendaViewModel


@Composable
fun HomeScreen(navController: NavController,
               innerPadding: PaddingValues,
               viewModel: AgendaViewModel
) {
    val contactos by viewModel.uiState.collectAsState()

    Scaffold(modifier = Modifier.fillMaxSize()) { padding ->
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(16.dp)
        ) {
            // BLOQUE 1: La lista o el mensaje
            // Usamos weight(1f) para que ocupe to-do el espacio disponible
            // y empuje el botón hacia abajo.
            Column(
                modifier = Modifier.weight(1f).fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (contactos.isEmpty()) {
                    Text("No se encontraron contactos.")
                } else {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(items = contactos) { contacto ->
                            Item(contacto, navController, viewModel)
                        }
                    }
                }
            }

            // BLOQUE 2: El botón (SIEMPRE VISIBLE)
            // Está fuera del if/else para que aparezca aunque no haya contactos
            Button(onClick = { navController.navigate("add-contact") }) {
                Text("Add Contact")
            }
        }
    }
}