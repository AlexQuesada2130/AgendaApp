package org.izv.aqs.agenda.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.izv.aqs.agenda.ui.model.Contacto
import org.izv.aqs.agenda.ui.viewmodel.AgendaViewModel

@Composable
fun EditContact(navController: NavController,
                innerPadding: PaddingValues,
                viewModel: AgendaViewModel
) {
    val contactoAEditar = viewModel.contactoSeleccionado
    var nombre by remember { mutableStateOf(contactoAEditar?.nombre ?: "") }
    var telefono by remember { mutableStateOf(contactoAEditar?.telefono ?:"") }
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "edit contact",
            style = MaterialTheme.typography.headlineMedium
        )
        OutlinedTextField(
            value = nombre,
            onValueChange = { nombre = it },
            label = { Text("Nombre") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = telefono,
            onValueChange = { telefono = it },
            label = { Text("Teléfono") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone)
        )
        Button(onClick = {
            // CREAMOS EL NUEVO CONTACTO Y SE LO PASAMOS AL VM
            val contactoNuevo = Contacto(nombre, telefono)
            viewModel.editarContacto(contactoNuevo)
            navController.popBackStack()
        }) {
            Text("Guardar Cambios")
        }
        Spacer(modifier = Modifier.height(24.dp))
        Button(onClick = { navController.navigate("add-contact") }) {
            Text("Add Contact")
        }
        Button(onClick = {
            if (contactoAEditar != null) {
                viewModel.eliminarContacto(contactoAEditar)
                navController.popBackStack()
            }
        }) {
            Text("Eliminar Contacto")
        }
        Button(onClick = { navController.popBackStack() }) {
            Text("back")
        }
    }
}