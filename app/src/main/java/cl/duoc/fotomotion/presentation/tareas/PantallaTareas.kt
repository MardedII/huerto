package cl.duoc.fotomotion.presentation.tareas

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cl.duoc.fotomotion.model.LaborHuerto
import cl.duoc.fotomotion.model.ParcelaHuerto
import cl.duoc.fotomotion.presentation.validation.ValidadorFormulario

@Composable
fun PantallaTareas(
    rolActual: String?,
    parcelas: List<ParcelaHuerto>,
    labores: List<LaborHuerto>,
    onRegistrarLabor: (descripcion: String, fecha: String, sectorId: Long) -> Unit,
    onEnviarEvidencia: (labor: LaborHuerto, comentario: String) -> Unit
) {
    // Estado Formulario Tarea Docente
    var descripcionTarea by remember { mutableStateOf("") }
    var fechaTarea by remember { mutableStateOf("") }
    var sectorTareaId by remember { mutableStateOf<Long?>(null) }
    var errorDescripcionTarea by remember { mutableStateOf(false) }
    var errorFechaRealTarea by remember { mutableStateOf(false) }
    var mensajeTareaGuardada by remember { mutableStateOf(false) }

    // Estado Estudiante / Evidencia
    var tareaSeleccionadaParaRealizar by remember { mutableStateOf<LaborHuerto?>(null) }
    var comentarioEstudiante by remember { mutableStateOf("") }
    var fotoAdjunta by remember { mutableStateOf(false) }
    var errorFotoEvidencia by remember { mutableStateOf(false) }
    var errorComentarioEvidencia by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        if (rolActual == "docente") {
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
                            text = "Nueva Tarea",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = descripcionTarea,
                        onValueChange = { descripcionTarea = it; errorDescripcionTarea = false },
                        label = { Text("Descripción de la tarea") },
                        leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
                        shape = RoundedCornerShape(12.dp),
                        isError = errorDescripcionTarea,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (errorDescripcionTarea) {
                        Text(
                            "Explica qué hay que hacer.",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    OutlinedTextField(
                        value = fechaTarea,
                        onValueChange = { fechaTarea = it; errorFechaRealTarea = false },
                        label = { Text("Fecha (AAAA-MM-DD)") },
                        leadingIcon = { Icon(Icons.Default.DateRange, contentDescription = null) },
                        shape = RoundedCornerShape(12.dp),
                        isError = errorFechaRealTarea,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (errorFechaRealTarea) {
                        Text(
                            "Escribe una fecha real: AAAA-MM-DD.",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        "Sector asociado:",
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.bodyMedium
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                        parcelas.forEach { p ->
                            val seleccionado = sectorTareaId == p.codigoParcela
                            Button(
                                onClick = { sectorTareaId = p.codigoParcela },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (seleccionado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                                    contentColor = if (seleccionado) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                                ),
                                shape = RoundedCornerShape(10.dp),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(p.tituloParcela)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            errorDescripcionTarea = !ValidadorFormulario.esTextoValido(descripcionTarea)
                            errorFechaRealTarea = !ValidadorFormulario.validarFechaReal(fechaTarea)
                            if (!errorDescripcionTarea && !errorFechaRealTarea) {
                                val sectorId = sectorTareaId ?: parcelas.firstOrNull()?.codigoParcela ?: 0L
                                onRegistrarLabor(descripcionTarea, fechaTarea, sectorId)
                                mensajeTareaGuardada = true
                                descripcionTarea = ""
                                fechaTarea = ""
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Guardar tarea", fontWeight = FontWeight.Bold)
                    }

                    if (mensajeTareaGuardada) {
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
                                Text("Tarea guardada.", color = MaterialTheme.colorScheme.onPrimaryContainer, fontWeight = FontWeight.Medium)
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }

        Text(
            "Lista de Tareas:",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        if (tareaSeleccionadaParaRealizar == null) {
            if (labores.isEmpty()) {
                Text(
                    "No hay tareas registradas.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            } else {
                labores.forEach { l ->
                    OutlinedCard(
                        shape = RoundedCornerShape(16.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp)
                    ) {
                        Column(modifier = Modifier.padding(14.dp)) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween,
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    text = l.detalleLabor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    modifier = Modifier.weight(1f)
                                )
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = MaterialTheme.colorScheme.secondaryContainer
                                ) {
                                    Text(
                                        text = "Fecha: ${l.diaPlanificado}",
                                        style = MaterialTheme.typography.labelSmall,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                                        modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                                    )
                                }
                            }

                            if (rolActual == "estudiante") {
                                Spacer(modifier = Modifier.height(10.dp))
                                Button(
                                    onClick = { tareaSeleccionadaParaRealizar = l },
                                    shape = RoundedCornerShape(10.dp),
                                    modifier = Modifier.align(Alignment.End)
                                ) {
                                    Icon(Icons.Default.PlayArrow, contentDescription = null, modifier = Modifier.size(16.dp))
                                    Spacer(modifier = Modifier.width(4.dp))
                                    Text("Realizar tarea", fontWeight = FontWeight.Bold)
                                }
                            }
                        }
                    }
                }
            }
        } else {
            // Formulario Enviar Evidencia Estudiante
            ElevatedCard(
                shape = RoundedCornerShape(20.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.List,
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Realizar Tarea",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Surface(
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        shape = RoundedCornerShape(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = "Detalle de Tarea: ${tareaSeleccionadaParaRealizar!!.detalleLabor}",
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(10.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = comentarioEstudiante,
                        onValueChange = { comentarioEstudiante = it; errorComentarioEvidencia = false },
                        label = { Text("Cuenta qué hiciste en el huerto.") },
                        leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
                        shape = RoundedCornerShape(12.dp),
                        isError = errorComentarioEvidencia,
                        modifier = Modifier.fillMaxWidth()
                    )
                    if (errorComentarioEvidencia) {
                        Text(
                            "Cuenta qué hiciste en el huerto.",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedButton(
                        onClick = { fotoAdjunta = true; errorFotoEvidencia = false },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Adjuntar foto")
                    }
                    if (errorFotoEvidencia) {
                        Text(
                            "Adjunta una foto con cámara o galería.",
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodySmall,
                            modifier = Modifier.padding(start = 4.dp, top = 2.dp)
                        )
                    }
                    if (fotoAdjunta) {
                        Spacer(modifier = Modifier.height(4.dp))
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = RoundedCornerShape(8.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier.padding(8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Done, contentDescription = null, tint = MaterialTheme.colorScheme.secondary)
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Foto adjuntada correctamente.", style = MaterialTheme.typography.bodySmall, fontWeight = FontWeight.Medium)
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = {
                            errorComentarioEvidencia = !ValidadorFormulario.esTextoValido(comentarioEstudiante)
                            errorFotoEvidencia = !fotoAdjunta
                            if (!errorComentarioEvidencia && !errorFotoEvidencia) {
                                onEnviarEvidencia(tareaSeleccionadaParaRealizar!!, comentarioEstudiante)
                                tareaSeleccionadaParaRealizar = null
                                comentarioEstudiante = ""
                                fotoAdjunta = false
                            }
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Icon(Icons.AutoMirrored.Filled.Send, contentDescription = null, modifier = Modifier.size(18.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Enviar evidencia", fontWeight = FontWeight.Bold)
                    }
                }
            }
        }
    }
}
