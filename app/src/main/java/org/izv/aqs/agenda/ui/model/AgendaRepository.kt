package org.izv.aqs.agenda.ui.model

import android.content.Context
import java.io.File

class AgendaRepository(private val context: Context) {

    private val fileName = "agenda.csv"

    // Obtener el archivo lo creamos en caso de que no exista
    private fun getFile(): File {
        return File(context.filesDir, fileName)
    }

    // 1. Listar Contactos: Lee el archivo y lo convierte en lista de objetos
    fun obtenerContactos(): List<Contacto> {
        val file = getFile()
        if (!file.exists()) return emptyList()

        return file.readLines().mapNotNull { linea ->
            // El formato CSV es: nombre,telefono
            val partes = linea.split(",")
            if (partes.size >= 2) {
                Contacto(partes[0], partes[1])
            } else {
                null
            }
        }
    }

    // 2. Agregar Contacto: Añade una línea al final
    fun agregarContacto(contacto: Contacto) {
        val file = getFile()
        // appendText añade al final sin borrar lo anterior
        file.appendText("${contacto.nombre},${contacto.telefono}\n")
    }

    // 3. Editar Contacto: Leemos to|do, modificamos y reescribimos to-do
    fun editarContacto(contactoOriginal: Contacto, contactoNuevo: Contacto) {
        val listaActual = obtenerContactos().toMutableList()
        val index = listaActual.indexOf(contactoOriginal)

        if (index != -1) {
            listaActual[index] = contactoNuevo
            guardarListaCompleta(listaActual)
        }
    }

    // 4. Eliminar Contacto
    fun eliminarContacto(contacto: Contacto) {
        val listaActual = obtenerContactos().toMutableList()
        listaActual.remove(contacto)
        guardarListaCompleta(listaActual)
    }

    // Méto-do para reescribir el archivo entero
    fun guardarListaCompleta(lista: List<Contacto>) {
        val file = getFile()
        // joinToString une todos los contactos separados por salto de línea
        val contenido = lista.joinToString("\n") { "${it.nombre},${it.telefono}" }
        file.writeText(contenido + "\n") // Escribimos to-do de nuevo
    }
}