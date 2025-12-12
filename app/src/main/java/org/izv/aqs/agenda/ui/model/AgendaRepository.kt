package org.izv.aqs.agenda.ui.model

import android.content.Context
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File

class AgendaRepository(private val context: Context) {

    private val fileName = "agenda.csv"

    // Obtener el archivo lo creamos en caso de que no exista
    private fun getFile(): File {
        return File(context.filesDir, fileName)
    }

    // 1. Listar Contactos: Lee el archivo y lo convierte en lista de objetos
    suspend fun obtenerContactos(): List<Contacto> = withContext(Dispatchers.IO) {
        val file = getFile()
        if (!file.exists()) return@withContext emptyList<Contacto>()

        file.readLines().mapNotNull { linea ->
            val partes = linea.split(",")
            if (partes.size >= 2) {
                Contacto(partes[0], partes[1])
            } else {
                null
            }
        }
    }

    // 2. Agregar Contacto: Añade una línea al final
    suspend fun agregarContacto(contacto: Contacto) = withContext(Dispatchers.IO) {
        val file = getFile()
        file.appendText("${contacto.nombre},${contacto.telefono}\n")
    }

    // 3. Editar Contacto: Leemos to|do, modificamos y reescribimos to-do
    suspend fun editarContacto(contactoOriginal: Contacto, contactoNuevo: Contacto) = withContext(Dispatchers.IO) {
        // Llamamos a obtenerContactos() que ya es suspend
        val listaActual = obtenerContactos().toMutableList()
        val index = listaActual.indexOf(contactoOriginal)

        if (index != -1) {
            listaActual[index] = contactoNuevo
            guardarListaCompleta(listaActual)
        }
    }

    // 4. Eliminar Contacto
    suspend fun eliminarContacto(contacto: Contacto) = withContext(Dispatchers.IO) {
        val listaActual = obtenerContactos().toMutableList()
        listaActual.remove(contacto)
        guardarListaCompleta(listaActual)
    }

    // Méto-do para reescribir el archivo entero
    private suspend fun guardarListaCompleta(lista: List<Contacto>) = withContext(Dispatchers.IO) {
        val file = getFile()
        val contenido = lista.joinToString("\n") { "${it.nombre},${it.telefono}" }
        file.writeText(contenido + "\n")
    }
}