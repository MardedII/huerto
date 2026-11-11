package cl.duoc.fotomotion.model

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface ConsultasHuerto {
    @Query("SELECT * FROM zonas_huerto")
    fun observarParcelas(): Flow<List<ParcelaHuerto>>

    @Insert
    suspend fun insertarParcela(parcela: ParcelaHuerto): Long

    @Update
    suspend fun actualizarParcela(parcela: ParcelaHuerto)

    @Delete
    suspend fun eliminarParcela(parcela: ParcelaHuerto)

    @Query("SELECT * FROM plantaciones")
    fun observarSiembras(): Flow<List<SiembraHuerto>>

    @Insert
    suspend fun insertarSiembra(siembra: SiembraHuerto): Long

    @Update
    suspend fun actualizarSiembra(siembra: SiembraHuerto)

    @Delete
    suspend fun eliminarSiembra(siembra: SiembraHuerto)

    @Query("SELECT * FROM actividades_huerto")
    fun observarLabores(): Flow<List<LaborHuerto>>

    @Insert
    suspend fun insertarLabor(labor: LaborHuerto): Long

    @Update
    suspend fun actualizarLabor(labor: LaborHuerto)

    @Delete
    suspend fun eliminarLabor(labor: LaborHuerto)
}
