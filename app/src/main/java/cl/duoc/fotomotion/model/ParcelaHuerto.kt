package cl.duoc.fotomotion.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "zonas_huerto")
data class ParcelaHuerto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val codigoParcela: Long = 0L,

    @ColumnInfo(name = "nombre")
    val tituloParcela: String = "",

    @ColumnInfo(name = "descripcion")
    val detalleParcela: String = "",

    @ColumnInfo(name = "ubicacion")
    val lugarParcela: String = "",

    @ColumnInfo(name = "estado")
    val situacionParcela: String = "ACTIVO",

    @ColumnInfo(name = "responsableId")
    val encargadoParcela: String = "docente",

    @ColumnInfo(name = "fechaCreacion")
    val diaRegistro: String = "",

    @ColumnInfo(name = "fechaActualizacion")
    val diaCambio: String = ""
)
