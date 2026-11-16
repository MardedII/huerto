package cl.duoc.fotomotion.presentation.navigation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ExitToApp
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import cl.duoc.fotomotion.model.BaseHuerto
import cl.duoc.fotomotion.presentation.HuertoViewModel
import cl.duoc.fotomotion.presentation.HuertoViewModelFactory
import cl.duoc.fotomotion.presentation.auth.PantallaAutenticacion
import cl.duoc.fotomotion.presentation.cultivos.PantallaCultivos
import cl.duoc.fotomotion.presentation.lecturas.PantallaLecturas
import cl.duoc.fotomotion.presentation.sectores.PantallaSectores
import cl.duoc.fotomotion.presentation.tareas.PantallaTareas
import cl.duoc.fotomotion.repository.RepositorioHuerto

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecorridoHuerto(
    huertoViewModel: HuertoViewModel = viewModel(
        factory = HuertoViewModelFactory(
            RepositorioHuerto(BaseHuerto.abrirBase(LocalContext.current).consultasHuerto())
        )
    )
) {
    val uiState by huertoViewModel.uiState.collectAsState()

    // Variables auxiliares para sector seleccionado en creación de cultivo y tarea
    var sectorSeleccionadoId by remember { mutableStateOf<Long?>(null) }

    Scaffold(
        topBar = {
            if (uiState.rolActual != null) {
                CenterAlignedTopAppBar(
                    title = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Surface(
                                shape = CircleShape,
                                color = MaterialTheme.colorScheme.primaryContainer,
                                modifier = Modifier.size(34.dp)
                            ) {
                                Box(contentAlignment = Alignment.Center) {
                                    Icon(
                                        imageVector = Icons.Default.Star,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.primary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = "Huerto Escolar",
                                fontWeight = FontWeight.Bold,
                                fontSize = 20.sp
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface
                    ),
                    actions = {
                        Button(
                            onClick = { huertoViewModel.cambiarRol(null) },
                            contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                            modifier = Modifier.padding(end = 8.dp)
                        ) {
                            Icon(
                                imageVector = Icons.AutoMirrored.Filled.ExitToApp,
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(text = "Salir", fontWeight = FontWeight.Bold)
                        }
                    }
                )
            }
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
        ) {
            if (uiState.rolActual == null) {
                // VISTA DE AUTENTICACIÓN (LOGIN & REGISTRO)
                PantallaAutenticacion(
                    onIngresarComoDocente = { huertoViewModel.cambiarRol("docente") },
                    onIngresarComoEstudiante = { huertoViewModel.cambiarRol("estudiante") }
                )
            } else {
                Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp)) {
                    // Pestañas / Navegación elegante
                    Card(
                        shape = RoundedCornerShape(16.dp),
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
                        modifier = Modifier.fillMaxWidth().padding(bottom = 12.dp)
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(6.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly
                        ) {
                            if (uiState.rolActual == "docente") {
                                FilterChip(
                                    selected = uiState.pestanaActual == "sectores",
                                    onClick = { huertoViewModel.cambiarPestana("sectores") },
                                    label = { Text("Sectores", fontWeight = FontWeight.Bold) },
                                    leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp)) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                        selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
                                    )
                                )
                                FilterChip(
                                    selected = uiState.pestanaActual == "cultivos",
                                    onClick = { huertoViewModel.cambiarPestana("cultivos") },
                                    label = { Text("Cultivos", fontWeight = FontWeight.Bold) },
                                    leadingIcon = { Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(16.dp)) },
                                    colors = FilterChipDefaults.filterChipColors(
                                        selectedContainerColor = MaterialTheme.colorScheme.primary,
                                        selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                        selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
                                    )
                                )
                            }
                            FilterChip(
                                selected = uiState.pestanaActual == "tareas",
                                onClick = { huertoViewModel.cambiarPestana("tareas") },
                                label = { Text("Tareas", fontWeight = FontWeight.Bold) },
                                leadingIcon = { Icon(Icons.AutoMirrored.Filled.List, contentDescription = null, modifier = Modifier.size(16.dp)) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                    selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                            FilterChip(
                                selected = uiState.pestanaActual == "lecturas",
                                onClick = { huertoViewModel.cambiarPestana("lecturas") },
                                label = { Text("Lecturas", fontWeight = FontWeight.Bold) },
                                leadingIcon = { Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(16.dp)) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                                    selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
                                )
                            )
                        }
                    }

                    when (uiState.pestanaActual) {
                        "sectores" -> {
                            if (uiState.rolActual == "docente") {
                                PantallaSectores(
                                    parcelas = uiState.parcelas,
                                    onRegistrarSector = { nombre, ubicacion ->
                                        huertoViewModel.registrarParcela(nombre, ubicacion)
                                    },
                                    onSeleccionarSector = { codigo ->
                                        sectorSeleccionadoId = codigo
                                    }
                                )
                            }
                        }
                        "cultivos" -> {
                            if (uiState.rolActual == "docente") {
                                PantallaCultivos(
                                    parcelas = uiState.parcelas,
                                    onRegistrarSiembra = { nombre, sectorId ->
                                        huertoViewModel.registrarSiembra(nombre, sectorId)
                                    }
                                )
                            }
                        }
                        "tareas" -> {
                            PantallaTareas(
                                rolActual = uiState.rolActual,
                                parcelas = uiState.parcelas,
                                labores = uiState.labores,
                                onRegistrarLabor = { descripcion, fecha, sectorId ->
                                    huertoViewModel.registrarLabor(descripcion, fecha, sectorId)
                                },
                                onEnviarEvidencia = { labor, comentario ->
                                    huertoViewModel.entregarEvidencia(labor, comentario)
                                }
                            )
                        }
                        "lecturas" -> {
                            PantallaLecturas()
                        }
                    }
                }
            }
        }
    }
}
