package cl.duoc.fotomotion.presentation.navigation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import cl.duoc.fotomotion.model.*
import cl.duoc.fotomotion.repository.RepositorioHuerto
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecorridoHuerto() {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val repositorio = remember {
        val db = BaseHuerto.abrirBase(context)
        RepositorioHuerto(db.consultasHuerto())
    }

    var rolActual by remember { mutableStateOf<String?>(null) } // "docente", "estudiante"
    var pestanaActual by remember { mutableStateOf("sectores") } // "sectores", "cultivos", "tareas", "lecturas"

    val parcelas by repositorio.leerParcelas().collectAsState(initial = emptyList())
    val siembras by repositorio.leerSiembras().collectAsState(initial = emptyList())
    val labores by repositorio.leerLabores().collectAsState(initial = emptyList())

    // Estado Formulario Sector
    var nombreSector by remember { mutableStateOf("") }
    var ubicacionSector by remember { mutableStateOf("") }
    var errorNombreSector by remember { mutableStateOf(false) }
    var errorUbicacionSector by remember { mutableStateOf(false) }
    var mensajeSectorGuardado by remember { mutableStateOf(false) }

    // Estado Formulario Cultivo
    var nombreCultivo by remember { mutableStateOf("") }
    var sectorSeleccionadoId by remember { mutableStateOf<Long?>(null) }
    var errorNombreCultivo by remember { mutableStateOf(false) }
    var errorSectorCultivo by remember { mutableStateOf(false) }
    var mensajeCultivoGuardado by remember { mutableStateOf(false) }

    // Estado Formulario Tarea
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

    // Estado Lecturas
    var humedadBaja by remember { mutableStateOf(true) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Huerto Escolar") },
                actions = {
                    if (rolActual != null) {
                        Button(onClick = {
                            rolActual = null
                            pestanaActual = "sectores"
                        }) {
                            Text(text = "Salir")
                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues).fillMaxSize()) {
            if (rolActual == null) {
                // Pantalla Bienvenida
                Column(
                    modifier = Modifier.fillMaxSize().padding(16.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { rolActual = "docente"; pestanaActual = "sectores" },
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    ) {
                        Text(text = "Ingresar como docente")
                    }
                    Button(
                        onClick = { rolActual = "estudiante"; pestanaActual = "tareas" },
                        modifier = Modifier.fillMaxWidth().padding(8.dp)
                    ) {
                        Text(text = "Ingresar como estudiante")
                    }
                }
            } else {
                Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
                    // Pestañas / Navegación
                    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                        if (rolActual == "docente") {
                            Button(onClick = { pestanaActual = "sectores" }) { Text("Sectores") }
                            Button(onClick = { pestanaActual = "cultivos" }) { Text("Cultivos") }
                        }
                        Button(onClick = { pestanaActual = "tareas" }) { Text("Tareas") }
                        Button(onClick = { pestanaActual = "lecturas" }) { Text("Lecturas") }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    when (pestanaActual) {
                        "sectores" -> {
                            if (rolActual == "docente") {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    OutlinedTextField(
                                        value = nombreSector,
                                        onValueChange = { nombreSector = it; errorNombreSector = false },
                                        label = { Text("Nombre del sector") },
                                        modifier = Modifier.fillMaxWidth()
                                            .semantics { if (errorNombreSector) contentDescription = "Error en Nombre del sector" }
                                    )
                                    if (errorNombreSector) {
                                        Text("Escribe el nombre del sector.", color = MaterialTheme.colorScheme.error)
                                    }

                                    OutlinedTextField(
                                        value = ubicacionSector,
                                        onValueChange = { ubicacionSector = it; errorUbicacionSector = false },
                                        label = { Text("Ubicación") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    if (errorUbicacionSector) {
                                        Text("Escribe dónde está el sector.", color = MaterialTheme.colorScheme.error)
                                    }

                                    Button(onClick = {
                                        errorNombreSector = nombreSector.isBlank()
                                        errorUbicacionSector = ubicacionSector.isBlank()
                                        if (!errorNombreSector && !errorUbicacionSector) {
                                            scope.launch {
                                                repositorio.registrarParcela(
                                                    ParcelaHuerto(
                                                        tituloParcela = nombreSector,
                                                        detalleParcela = "Detalle",
                                                        lugarParcela = ubicacionSector
                                                    )
                                                )
                                                mensajeSectorGuardado = true
                                                nombreSector = ""
                                                ubicacionSector = ""
                                            }
                                        }
                                    }, modifier = Modifier.padding(top = 8.dp)) {
                                        Text("Guardar sector")
                                    }

                                    if (mensajeSectorGuardado) {
                                        Text("Sector guardado.", color = MaterialTheme.colorScheme.primary)
                                    }

                                    Spacer(modifier = Modifier.height(16.dp))
                                    Text("Sectores registrados:")
                                    LazyColumn {
                                        items(parcelas) { p ->
                                            Text(
                                                text = p.tituloParcela,
                                                modifier = Modifier.fillMaxWidth().clickable {
                                                    sectorSeleccionadoId = p.codigoParcela
                                                    sectorTareaId = p.codigoParcela
                                                }.padding(8.dp)
                                            )
                                        }
                                    }
                                }
                            }
                        }
                        "cultivos" -> {
                            if (rolActual == "docente") {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    OutlinedTextField(
                                        value = nombreCultivo,
                                        onValueChange = { nombreCultivo = it; errorNombreCultivo = false },
                                        label = { Text("Nombre del cultivo") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    if (errorNombreCultivo) {
                                        Text("Escribe el nombre del cultivo.", color = MaterialTheme.colorScheme.error)
                                    }

                                    Text("Selecciona un sector:")
                                    if (errorSectorCultivo) {
                                        Text("Selecciona un sector.", color = MaterialTheme.colorScheme.error)
                                    }
                                    LazyColumn(modifier = Modifier.height(120.dp)) {
                                        items(parcelas) { p ->
                                            val seleccionado = sectorSeleccionadoId == p.codigoParcela
                                            Button(
                                                onClick = { sectorSeleccionadoId = p.codigoParcela; errorSectorCultivo = false },
                                                colors = ButtonDefaults.buttonColors(
                                                    containerColor = if (seleccionado) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary
                                                ),
                                                modifier = Modifier.fillMaxWidth().padding(2.dp)
                                            ) {
                                                Text(p.tituloParcela)
                                            }
                                        }
                                    }

                                    Button(onClick = {
                                        errorNombreCultivo = nombreCultivo.isBlank()
                                        errorSectorCultivo = sectorSeleccionadoId == null
                                        if (!errorNombreCultivo && !errorSectorCultivo && sectorSeleccionadoId != null) {
                                            scope.launch {
                                                repositorio.registrarSiembra(
                                                    SiembraHuerto(
                                                        tituloSiembra = nombreCultivo,
                                                        parcelaAsociada = sectorSeleccionadoId!!
                                                    )
                                                )
                                                mensajeCultivoGuardado = true
                                                nombreCultivo = ""
                                            }
                                        }
                                    }, modifier = Modifier.padding(top = 8.dp)) {
                                        Text("Guardar cultivo")
                                    }

                                    if (mensajeCultivoGuardado) {
                                        Text("Cultivo guardado.", color = MaterialTheme.colorScheme.primary)
                                    }
                                }
                            }
                        }
                        "tareas" -> {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                if (rolActual == "docente") {
                                    OutlinedTextField(
                                        value = descripcionTarea,
                                        onValueChange = { descripcionTarea = it; errorDescripcionTarea = false },
                                        label = { Text("Descripción de la tarea") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    if (errorDescripcionTarea) {
                                        Text("Explica qué hay que hacer.", color = MaterialTheme.colorScheme.error)
                                    }

                                    OutlinedTextField(
                                        value = fechaTarea,
                                        onValueChange = { fechaTarea = it; errorFechaRealTarea = false },
                                        label = { Text("Fecha (AAAA-MM-DD)") },
                                        modifier = Modifier.fillMaxWidth()
                                    )
                                    if (errorFechaRealTarea) {
                                        Text("Escribe una fecha real: AAAA-MM-DD.", color = MaterialTheme.colorScheme.error)
                                    }

                                    Text("Sector asociado:")
                                    LazyColumn(modifier = Modifier.height(100.dp)) {
                                        items(parcelas) { p ->
                                            Button(
                                                onClick = { sectorTareaId = p.codigoParcela },
                                                modifier = Modifier.fillMaxWidth().padding(2.dp)
                                            ) {
                                                Text(p.tituloParcela)
                                            }
                                        }
                                    }

                                    Button(onClick = {
                                        errorDescripcionTarea = descripcionTarea.isBlank()
                                        // Validar formato fecha real AAAA-MM-DD (e.g. 2026-02-30 es invalida)
                                        errorFechaRealTarea = !validarFechaReal(fechaTarea)
                                        if (!errorDescripcionTarea && !errorFechaRealTarea) {
                                            val sectorId = sectorTareaId ?: parcelas.firstOrNull()?.codigoParcela ?: 0L
                                            scope.launch {
                                                repositorio.registrarLabor(
                                                    LaborHuerto(
                                                        detalleLabor = descripcionTarea,
                                                        diaPlanificado = fechaTarea,
                                                        parcelaTrabajo = sectorId
                                                    )
                                                )
                                                mensajeTareaGuardada = true
                                                descripcionTarea = ""
                                                fechaTarea = ""
                                            }
                                        }
                                    }, modifier = Modifier.padding(top = 8.dp)) {
                                        Text("Guardar tarea")
                                    }

                                    if (mensajeTareaGuardada) {
                                        Text("Tarea guardada.", color = MaterialTheme.colorScheme.primary)
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))
                                Text("Lista de Tareas:")
                                if (tareaSeleccionadaParaRealizar == null) {
                                    LazyColumn {
                                        items(labores) { l ->
                                            Card(modifier = Modifier.fillMaxWidth().padding(4.dp)) {
                                                Column(modifier = Modifier.padding(8.dp)) {
                                                    Text(text = l.detalleLabor)
                                                    Text(text = "Fecha: ${l.diaPlanificado}")
                                                    if (rolActual == "estudiante") {
                                                        Button(onClick = {
                                                            tareaSeleccionadaParaRealizar = l
                                                        }) {
                                                            Text("Realizar tarea")
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    // Pantalla Enviar Evidencia
                                    Column(modifier = Modifier.fillMaxWidth()) {
                                        Text("Detalle de Tarea: ${tareaSeleccionadaParaRealizar!!.detalleLabor}")
                                        OutlinedTextField(
                                            value = comentarioEstudiante,
                                            onValueChange = { comentarioEstudiante = it; errorComentarioEvidencia = false },
                                            label = { Text("Cuenta qué hiciste en el huerto.") },
                                            modifier = Modifier.fillMaxWidth()
                                        )
                                        if (errorComentarioEvidencia) {
                                            Text("Cuenta qué hiciste en el huerto.", color = MaterialTheme.colorScheme.error)
                                        }

                                        Button(onClick = { fotoAdjunta = true; errorFotoEvidencia = false }) {
                                            Text("Adjuntar foto")
                                        }
                                        if (errorFotoEvidencia) {
                                            Text("Adjunta una foto con cámara o galería.", color = MaterialTheme.colorScheme.error)
                                        }
                                        if (fotoAdjunta) {
                                            Text("Foto adjuntada correctamente.")
                                        }

                                        Button(onClick = {
                                            errorComentarioEvidencia = comentarioEstudiante.isBlank()
                                            errorFotoEvidencia = !fotoAdjunta
                                            if (!errorComentarioEvidencia && errorFotoEvidencia == false) {
                                                scope.launch {
                                                    repositorio.corregirLabor(
                                                        tareaSeleccionadaParaRealizar!!.copy(
                                                            situacionLabor = "ENTREGADA",
                                                            notaEntrega = comentarioEstudiante,
                                                            imagenLabor = "foto_ficticia.jpg"
                                                        )
                                                    )
                                                    tareaSeleccionadaParaRealizar = null
                                                    comentarioEstudiante = ""
                                                    fotoAdjunta = false
                                                }
                                            }
                                        }, modifier = Modifier.padding(top = 8.dp)) {
                                            Text("Enviar evidencia")
                                        }
                                    }
                                }
                            }
                        }
                        "lecturas" -> {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Text("Lecturas del Huerto")
                                if (humedadBaja) {
                                    Text("Humedad baja: revisar el riego.")
                                }
                                Button(onClick = { humedadBaja = !humedadBaja }, modifier = Modifier.padding(top = 8.dp)) {
                                    Text("Simular cambio de humedad")
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

fun validarFechaReal(fechaStr: String): Boolean {
    val regex = Regex("^\\d{4}-\\d{2}-\\d{2}$")
    if (!regex.matches(fechaStr)) return false
    val partes = fechaStr.split("-")
    val anio = partes[0].toIntOrNull() ?: return false
    val mes = partes[1].toIntOrNull() ?: return false
    val dia = partes[2].toIntOrNull() ?: return false
    if (mes < 1 || mes > 12) return false
    val diasMes = when (mes) {
        2 -> if (anio % 4 == 0 && (anio % 100 != 0 || anio % 400 == 0)) 29 else 28
        4, 6, 9, 11 -> 30
        else -> 31
    }
    return dia in 1..diasMes
}
