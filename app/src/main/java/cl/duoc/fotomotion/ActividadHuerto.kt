package cl.duoc.fotomotion

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.lightColorScheme
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cl.duoc.fotomotion.presentation.navigation.RecorridoHuerto

class ActividadHuerto : ComponentActivity() {
    @Suppress("PARAMETER_NAME_CHANGED_ON_OVERRIDE")
    override fun onCreate(estadoGuardado: Bundle?) {
        super.onCreate(estadoGuardado)
        enableEdgeToEdge()
        setContent {
            MaterialTheme(
                colorScheme = lightColorScheme(
                    primary = Color(0xFF2D6A4F),
                    onPrimary = Color(0xFFFFFFFF),
                    primaryContainer = Color(0xFFD8F3DC),
                    onPrimaryContainer = Color(0xFF081C15),
                    secondary = Color(0xFFD97706),
                    onSecondary = Color(0xFFFFFFFF),
                    secondaryContainer = Color(0xFFFEF3C7),
                    onSecondaryContainer = Color(0xFF78350F),
                    tertiary = Color(0xFF1B4D3E),
                    onTertiary = Color(0xFFFFFFFF),
                    tertiaryContainer = Color(0xFFB7E4C7),
                    onTertiaryContainer = Color(0xFF002117),
                    background = Color(0xFFF4F7F4),
                    onBackground = Color(0xFF1B241E),
                    surface = Color(0xFFFFFFFF),
                    onSurface = Color(0xFF1B241E),
                    surfaceVariant = Color(0xFFE2EBE2),
                    onSurfaceVariant = Color(0xFF404A42),
                    outline = Color(0xFF707B72),
                    error = Color(0xFFBA1A1A),
                    onError = Color(0xFFFFFFFF)
                ),
                shapes = Shapes(
                    extraSmall = RoundedCornerShape(8.dp),
                    small = RoundedCornerShape(12.dp),
                    medium = RoundedCornerShape(16.dp),
                    large = RoundedCornerShape(24.dp),
                    extraLarge = RoundedCornerShape(32.dp)
                )
            ) {
                RecorridoHuerto()
            }
        }
    }
}
