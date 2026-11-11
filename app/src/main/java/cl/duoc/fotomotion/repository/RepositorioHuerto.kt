package cl.duoc.fotomotion.repository

import cl.duoc.fotomotion.model.ConsultasHuerto
import cl.duoc.fotomotion.model.LaborHuerto
import cl.duoc.fotomotion.model.ParcelaHuerto
import cl.duoc.fotomotion.model.SiembraHuerto
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first

class RepositorioHuerto(private val consultas: ConsultasHuerto) {
    fun leerParcelas(): Flow<List<ParcelaHuerto>> = consultas.observarParcelas()
    suspend fun registrarParcela(parcela: ParcelaHuerto) = consultas.insertarParcela(parcela)
    suspend fun corregirParcela(parcela: ParcelaHuerto) = consultas.actualizarParcela(parcela)
    suspend fun borrarParcela(parcela: ParcelaHuerto): Boolean {
        val siembras = consultas.observarSiembras().first()
        val tieneSiembras = siembras.any { it.parcelaAsociada == parcela.codigoParcela }
        val labores = consultas.observarLabores().first()
        val tieneLabores = labores.any { it.parcelaTrabajo == parcela.codigoParcela }
        if (tieneSiembras || tieneLabores) {
            return false
        }
        consultas.eliminarParcela(parcela)
        return true
    }

    fun leerSiembras(): Flow<List<SiembraHuerto>> = consultas.observarSiembras()
    suspend fun registrarSiembra(siembra: SiembraHuerto) = consultas.insertarSiembra(siembra)
    suspend fun corregirSiembra(siembra: SiembraHuerto) = consultas.actualizarSiembra(siembra)
    suspend fun borrarSiembra(siembra: SiembraHuerto): Boolean {
        val labores = consultas.observarLabores().first()
        val tieneLabores = labores.any { it.siembraTrabajo == siembra.codigoSiembra }
        if (tieneLabores) {
            return false
        }
        consultas.eliminarSiembra(siembra)
        return true
    }

    fun leerLabores(): Flow<List<LaborHuerto>> = consultas.observarLabores()
    suspend fun registrarLabor(labor: LaborHuerto) = consultas.insertarLabor(labor)
    suspend fun corregirLabor(labor: LaborHuerto) = consultas.actualizarLabor(labor)
    suspend fun borrarLabor(labor: LaborHuerto) = consultas.eliminarLabor(labor)
}
