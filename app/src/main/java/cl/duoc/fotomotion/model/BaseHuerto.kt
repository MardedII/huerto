package cl.duoc.fotomotion.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ParcelaHuerto::class, SiembraHuerto::class, LaborHuerto::class], version = 1, exportSchema = true)
abstract class BaseHuerto : RoomDatabase() {
    abstract fun consultasHuerto(): ConsultasHuerto

    companion object {
        @Volatile
        private var INSTANCIA: BaseHuerto? = null

        fun abrirBase(context: Context): BaseHuerto {
            return INSTANCIA ?: synchronized(this) {
                Room.databaseBuilder(
                    context.applicationContext,
                    BaseHuerto::class.java,
                    "registro_huerto_escolar.db"
                ).build().also { INSTANCIA = it }
            }
        }
    }
}
