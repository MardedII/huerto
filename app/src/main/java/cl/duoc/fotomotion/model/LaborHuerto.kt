package cl.duoc.fotomotion.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "actividades_huerto")
data class LaborHuerto(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val codigoLabor: Long = 0L,

    @ColumnInfo(name = "tipo")
    val claseLabor: String = "TAREA",

    @ColumnInfo(name = "descripcion")
    val detalleLabor: String = "",

    @ColumnInfo(name = "sectorId")
    val parcelaTrabajo: Long = 0L,

    @ColumnInfo(name = "cultivoId")
    val siembraTrabajo: Long? = null,

    @ColumnInfo(name = "fechaCreacion")
    val diaCreacion: String = "",

    @ColumnInfo(name = "fechaProgramada")
    val diaPlanificado: String = "",

    @ColumnInfo(name = "responsableId")
    val encargadoLabor: String = "estudiante",

    @ColumnInfo(name = "autorLabor")
    val autorLabor: String = "docente",

    @ColumnInfo(name = "estado")
    val situacionLabor: String = "PENDIENTE",

    @ColumnInfo(name = "fechaHoraRealizacion")
    val momentoEntrega: String? = null,

    @ColumnInfo(name = "fotografiaAdjunta")
    val imagenLabor: String? = null,

    @ColumnInfo(name = "comentarioEstudiante")
    val notaEntrega: String? = null,

    @ColumnInfo(name = "comentarioDocente")
    val notaRevision: String? = null,

    @ColumnInfo(name = "validadorId")
    val revisorLabor: String? = null,

    @ColumnInfo(name = "fechaHoraValidacion")
    val momentoRevision: String? = null
)
