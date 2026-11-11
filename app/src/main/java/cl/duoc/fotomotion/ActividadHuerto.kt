package cl.duoc.fotomotion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import cl.duoc.fotomotion.presentation.navigation.RecorridoHuerto

class ActividadHuerto : ComponentActivity() {
    // onCreate mantiene su nombre porque Android lo exige.
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun onCreate(estadoGuardado: Bundle?) {
        super.onCreate(estadoGuardado)
        enableEdgeToEdge()
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF4C6444),
                    onPrimary = Color(0xFFFFFFFF),
                    primaryContainer = Color(0xFFD3E8C8),
                    onPrimaryContainer = Color(0xFF0F200C),
                    secondary = Color(0xFFB87D4B),
                    onSecondary = Color(0xFFFFFFFF),
                    secondaryContainer = Color(0xFFFDE1CE),
                    onSecondaryContainer = Color(0xFF311300),
                    background = Color(0xFFF7F9F5),
                    onBackground = Color(0xFF191D17),
                    surface = Color(0xFFF7F9F5),
                    onSurface = Color(0xFF191D17),
                    surfaceVariant = Color(0xFFE2E9DE),
                    onSurfaceVariant = Color(0xFF444841)
                )
            ) {
                RecorridoHuerto()
            }
        }
    }
}
