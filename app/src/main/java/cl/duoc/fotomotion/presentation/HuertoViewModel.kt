package cl.duoc.fotomotion.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import cl.duoc.fotomotion.model.LaborHuerto
import cl.duoc.fotomotion.model.ParcelaHuerto
import cl.duoc.fotomotion.model.SiembraHuerto
import cl.duoc.fotomotion.presentation.state.HuertoUiState
import cl.duoc.fotomotion.repository.RepositorioHuerto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class HuertoViewModel(private val repositorio: RepositorioHuerto) : ViewModel() {

    private val _uiState = MutableStateFlow(HuertoUiState())
    val uiState: StateFlow<HuertoUiState> = _uiState.asStateFlow()

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            repositorio.leerParcelas().collect { listaParcelas ->
                _uiState.update { it.copy(parcelas = listaParcelas) }
            }
        }
        viewModelScope.launch {
            repositorio.leerSiembras().collect { listaSiembras ->
                _uiState.update { it.copy(siembras = listaSiembras) }
            }
        }
        viewModelScope.launch {
            repositorio.leerLabores().collect { listaLabores ->
                _uiState.update { it.copy(labores = listaLabores) }
            }
        }
    }

    fun cambiarRol(nuevoRol: String?) {
        val pestanaInicial = if (nuevoRol == "estudiante") "tareas" else "sectores"
        _uiState.update {
            it.copy(
                rolActual = nuevoRol,
                pestanaActual = pestanaInicial
            )
        }
    }

    fun cambiarPestana(nuevaPestana: String) {
        _uiState.update { it.copy(pestanaActual = nuevaPestana) }
    }

    fun registrarParcela(nombre: String, ubicacion: String) {
        viewModelScope.launch {
            repositorio.registrarParcela(
                ParcelaHuerto(
                    tituloParcela = nombre,
                    detalleParcela = "Detalle",
                    lugarParcela = ubicacion
                )
            )
        }
    }

    fun registrarSiembra(nombre: String, sectorId: Long) {
        viewModelScope.launch {
            repositorio.registrarSiembra(
                SiembraHuerto(
                    tituloSiembra = nombre,
                    parcelaAsociada = sectorId
                )
            )
        }
    }

    fun registrarLabor(descripcion: String, fecha: String, sectorId: Long) {
        viewModelScope.launch {
            repositorio.registrarLabor(
                LaborHuerto(
                    detalleLabor = descripcion,
                    diaPlanificado = fecha,
                    parcelaTrabajo = sectorId
                )
            )
        }
    }

    fun entregarEvidencia(labor: LaborHuerto, comentario: String) {
        viewModelScope.launch {
            repositorio.corregirLabor(
                labor.copy(
                    situacionLabor = "ENTREGADA",
                    notaEntrega = comentario,
                    imagenLabor = "foto_ficticia.jpg"
                )
            )
        }
    }
}

class HuertoViewModelFactory(private val repositorio: RepositorioHuerto) : ViewModelProvider.Factory {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HuertoViewModel::class.java)) {
            return HuertoViewModel(repositorio) as T
        }
        throw IllegalArgumentException("Clase ViewModel desconocida: ${modelClass.name}")
    }
}
