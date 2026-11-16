package cl.duoc.fotomotion.presentation.state

import cl.duoc.fotomotion.model.LaborHuerto
import cl.duoc.fotomotion.model.ParcelaHuerto
import cl.duoc.fotomotion.model.SiembraHuerto

data class HuertoUiState(
    val rolActual: String? = null, // "docente", "estudiante"
    val pestanaActual: String = "sectores", // "sectores", "cultivos", "tareas", "lecturas"
    val parcelas: List<ParcelaHuerto> = emptyList(),
    val siembras: List<SiembraHuerto> = emptyList(),
    val labores: List<LaborHuerto> = emptyList()
)
