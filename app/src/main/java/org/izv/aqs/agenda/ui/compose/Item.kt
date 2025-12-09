package org.izv.aqs.agenda.ui.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.izv.aqs.agenda.ui.model.Contacto
import org.izv.aqs.agenda.ui.viewmodel.AgendaViewModel


@Composable
fun Item(contacto: Contacto,
         navController: NavController,
         viewModel: AgendaViewModel
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        onClick = {
            viewModel.contactoSeleccionado = contacto
            navController.navigate("edit-contact")}
    ) {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = "${contacto.nombre}   ${contacto.telefono}")
        }
    }
}