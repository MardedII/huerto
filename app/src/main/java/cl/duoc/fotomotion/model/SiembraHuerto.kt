package cl.duoc.fotomotion.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plantaciones")
data class SiembraHuerto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val codigoSiembra: Long = 0L,

    @ColumnInfo(name = "nombreComun")
    val tituloSiembra: String = "",

    @ColumnInfo(name = "descripcion")
    val detalleSiembra: String = "",

    @ColumnInfo(name = "sectorId")
    val parcelaAsociada: Long = 0L,

    @ColumnInfo(name = "fechaSiembra")
    val diaSiembra: String? = null,

    @ColumnInfo(name = "fechaTrasplante")
    val diaTraslado: String? = null,

    @ColumnInfo(name = "fechaEstimadaCosecha")
    val diaCosecha: String? = null,

    @ColumnInfo(name = "frecuenciaRiegoDias")
    val intervaloRiego: Int = 1,

    @ColumnInfo(name = "estado")
    val situacionSiembra: String = "ACTIVO",

    @ColumnInfo(name = "observaciones")
    val notasSiembra: String = "",

    @ColumnInfo(name = "fotografiaReferencialFicticia")
    val imagenSiembra: String? = null
)
