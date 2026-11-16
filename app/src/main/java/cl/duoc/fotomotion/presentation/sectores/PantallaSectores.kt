package cl.duoc.fotomotion.presentation.sectores

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.fotomotion.model.ParcelaHuerto
import cl.duoc.fotomotion.presentation.validation.ValidadorFormulario

@Composable
fun PantallaSectores(
    parcelas: List<ParcelaHuerto>,
    onRegistrarSector: (nombre: String, ubicacion: String) -> Unit,
    onSeleccionarSector: (codigoParcela: Long) -> Unit
) {
    var nombreSector by remember { mutableStateOf("") }
    var ubicacionSector by remember { mutableStateOf("") }
    var errorNombreSector by remember { mutableStateOf(false) }
    var errorUbicacionSector by remember { mutableStateOf(false) }
    var mensajeSectorGuardado by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        ElevatedCard(
            shape = RoundedCornerShape(20.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(modifier = Modifier.padding(16.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Nuevo Sector",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = nombreSector,
                    onValueChange = { nombreSector = it; errorNombreSector = false },
                    label = { Text("Nombre del sector") },
                    leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
                    shape = RoundedCornerShape(12.dp),
                    isError = errorNombreSector,
                    modifier = Modifier
                        .fillMaxWidth()
                        .semantics { if (errorNombreSector) contentDescription = "Error en Nombre del sector" }
                )
                if (errorNombreSector) {
                    Text(
                        "Escribe el nombre del sector.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                OutlinedTextField(
                    value = ubicacionSector,
                    onValueChange = { ubicacionSector = it; errorUbicacionSector = false },
                    label = { Text("Ubicación") },
                    leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null) },
                    shape = RoundedCornerShape(12.dp),
                    isError = errorUbicacionSector,
                    modifier = Modifier.fillMaxWidth()
                )
                if (errorUbicacionSector) {
                    Text(
                        "Escribe dónde está el sector.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        errorNombreSector = !ValidadorFormulario.esTextoValido(nombreSector)
                        errorUbicacionSector = !ValidadorFormulario.esTextoValido(ubicacionSector)
                        if (!errorNombreSector && !errorUbicacionSector) {
                            onRegistrarSector(nombreSector, ubicacionSector)
                            mensajeSectorGuardado = true
                            nombreSector = ""
                            ubicacionSector = ""
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Guardar sector", fontWeight = FontWeight.Bold)
                }

                if (mensajeSectorGuardado) {
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = RoundedCornerShape(8.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier.padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(Icons.Default.Done, contentDescription = null, tint = MaterialTheme.colorScheme.primary)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Sector guardado.", color = MaterialTheme.colorScheme.onPrimaryContainer, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            "Sectores registrados:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (parcelas.isEmpty()) {
            Text(
                "No hay sectores registrados aún.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        } else {
            parcelas.forEach { p ->
                OutlinedCard(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp)
                        .clickable { onSeleccionarSector(p.codigoParcela) },
                    shape = RoundedCornerShape(12.dp)
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.LocationOn,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = p.tituloParcela,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp
                            )
                            if (p.lugarParcela.isNotBlank()) {
                                Text(
                                    text = "Ubicación: ${p.lugarParcela}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}
