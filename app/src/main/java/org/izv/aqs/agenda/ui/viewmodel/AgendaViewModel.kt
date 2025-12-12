package org.izv.aqs.agenda.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.izv.aqs.agenda.ui.model.AgendaRepository
import org.izv.aqs.agenda.ui.model.Contacto

class AgendaViewModel(application: Application) : AndroidViewModel(application) {

    // 1. Instanciamos el repositorio pasándole el contexto de la aplicación
    private val repository = AgendaRepository(application)

    // 2. Estado de la UI (La lista de contactos)
    // Usamos StateFlow: es como una lista "viva" que avisa cuando cambia.
    // _uiState es privada para que solo este ViewModel pueda modificarla.
    private val _uiState = MutableStateFlow<List<Contacto>>(emptyList())

    // uiState es la versión pública que la Vista leerá (solo lectura).
    val uiState: StateFlow<List<Contacto>> = _uiState.asStateFlow()

    // Variable para guardar temporalmente el contacto que queremos editar
    var contactoSeleccionado: Contacto? = null

    // 3. Al iniciar el ViewModel, cargamos los datos del archivo
    init {
        cargarContactos()
    }

    // --- MÉTODOS PÚBLICOS PARA LA VISTA ---

    // Le dice al repo que lea el archivo y actualiza nuestra lista _uiState
    fun cargarContactos() {
        // Lanzamos una corrutina en el scope del ViewModel
        viewModelScope.launch {
            _uiState.value = repository.obtenerContactos()
        }
    }

    fun agregarContacto(nombre: String, telefono: String) {
        viewModelScope.launch {
            val nuevoContacto = Contacto(nombre, telefono)
            repository.agregarContacto(nuevoContacto)
            cargarContactos() // Recargamos la lista tras la operación
        }
    }

    fun editarContacto(contactoNuevo: Contacto) {
        viewModelScope.launch {
            contactoSeleccionado?.let { viejo ->
                repository.editarContacto(viejo, contactoNuevo)
                cargarContactos()
            }
        }
    }
    fun eliminarContacto(contacto: Contacto) {
        viewModelScope.launch {
            repository.eliminarContacto(contacto)
            cargarContactos()
        }
    }
}