package cl.duoc.fotomotion.presentation.cultivos

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cl.duoc.fotomotion.model.ParcelaHuerto
import cl.duoc.fotomotion.presentation.validation.ValidadorFormulario

@Composable
fun PantallaCultivos(
    parcelas: List<ParcelaHuerto>,
    onRegistrarSiembra: (nombreCultivo: String, sectorId: Long) -> Unit
) {
    var nombreCultivo by remember { mutableStateOf("") }
    var sectorSeleccionadoId by remember { mutableStateOf<Long?>(null) }
    var errorNombreCultivo by remember { mutableStateOf(false) }
    var errorSectorCultivo by remember { mutableStateOf(false) }
    var mensajeCultivoGuardado by remember { mutableStateOf(false) }

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
                        imageVector = Icons.Default.Star,
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Nuevo Cultivo",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                OutlinedTextField(
                    value = nombreCultivo,
                    onValueChange = { nombreCultivo = it; errorNombreCultivo = false },
                    label = { Text("Nombre del cultivo") },
                    leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
                    shape = RoundedCornerShape(12.dp),
                    isError = errorNombreCultivo,
                    modifier = Modifier.fillMaxWidth()
                )
                if (errorNombreCultivo) {
                    Text(
                        "Escribe el nombre del cultivo.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(12.dp))

                Text(
                    "Selecciona un sector:",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.bodyMedium
                )
                if (errorSectorCultivo) {
                    Text(
                        "Selecciona un sector.",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.bodySmall,
                        modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                    )
                }

                Spacer(modifier = Modifier.height(6.dp))

                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    parcelas.forEach { p ->
                        val seleccionado = sectorSeleccionadoId == p.codigoParcela
                        Button(
                            onClick = {
                                sectorSeleccionadoId = p.codigoParcela
                                errorSectorCultivo = false
                            },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (seleccionado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                contentColor = if (seleccionado) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                            ),
                            shape = RoundedCornerShape(10.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.Start,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(
                                    if (seleccionado) Icons.Default.Done else Icons.Default.LocationOn,
                                    contentDescription = null,
                                    modifier = Modifier.size(16.dp)
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                Text(p.tituloParcela)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(12.dp))

                Button(
                    onClick = {
                        errorNombreCultivo = !ValidadorFormulario.esTextoValido(nombreCultivo)
                        errorSectorCultivo = sectorSeleccionadoId == null
                        if (!errorNombreCultivo && !errorSectorCultivo && sectorSeleccionadoId != null) {
                            onRegistrarSiembra(nombreCultivo, sectorSeleccionadoId!!)
                            mensajeCultivoGuardado = true
                            nombreCultivo = ""
                        }
                    },
                    shape = RoundedCornerShape(12.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Guardar cultivo", fontWeight = FontWeight.Bold)
                }

                if (mensajeCultivoGuardado) {
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
                            Text("Cultivo guardado.", color = MaterialTheme.colorScheme.onPrimaryContainer, fontWeight = FontWeight.Medium)
                        }
                    }
                }
            }
        }
    }
}
