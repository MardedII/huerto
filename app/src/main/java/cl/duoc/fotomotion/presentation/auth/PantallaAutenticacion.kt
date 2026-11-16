package cl.duoc.fotomotion.presentation.auth

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaAutenticacion(
    onIngresarComoDocente: () -> Unit,
    onIngresarComoEstudiante: () -> Unit
) {
    var pestanaAuth by remember { mutableIntStateOf(0) } // 0: Iniciar Sesión, 1: Registrarse

    // Campos Login
    var correoLogin by remember { mutableStateOf("") }
    var claveLogin by remember { mutableStateOf("") }
    var rolSeleccionadoLogin by remember { mutableStateOf("docente") }
    var verClaveLogin by remember { mutableStateOf(false) }

    // Campos Registro
    var nombreRegistro by remember { mutableStateOf("") }
    var correoRegistro by remember { mutableStateOf("") }
    var claveRegistro by remember { mutableStateOf("") }
    var rolSeleccionadoRegistro by remember { mutableStateOf("estudiante") }
    var verClaveRegistro by remember { mutableStateOf(false) }
    var mensajeRegistroExitoso by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Logo superior
        Surface(
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primaryContainer,
            shadowElevation = 6.dp,
            modifier = Modifier.size(80.dp)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.size(44.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Huerto Escolar",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onBackground
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Pestañas Iniciar Sesión / Registrarse
        Card(
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant),
            modifier = Modifier.fillMaxWidth()
        ) {
            TabRow(
                selectedTabIndex = pestanaAuth,
                containerColor = Color.Transparent,
                contentColor = MaterialTheme.colorScheme.primary,
                indicator = {}
            ) {
                Tab(
                    selected = pestanaAuth == 0,
                    onClick = { pestanaAuth = 0 },
                    text = {
                        Text(
                            "Iniciar Sesión",
                            fontWeight = if (pestanaAuth == 0) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 15.sp
                        )
                    }
                )
                Tab(
                    selected = pestanaAuth == 1,
                    onClick = { pestanaAuth = 1 },
                    text = {
                        Text(
                            "Registrarse",
                            fontWeight = if (pestanaAuth == 1) FontWeight.Bold else FontWeight.Normal,
                            fontSize = 15.sp
                        )
                    }
                )
            }
        }

        Spacer(modifier = Modifier.height(20.dp))

        ElevatedCard(
            shape = RoundedCornerShape(24.dp),
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 6.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (pestanaAuth == 0) {
                    // --- VISTA INICIAR SESIÓN ---
                    Text(
                        text = "Bienvenido de nuevo",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = correoLogin,
                        onValueChange = { correoLogin = it },
                        label = { Text("Correo o Usuario") },
                        leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = claveLogin,
                        onValueChange = { claveLogin = it },
                        label = { Text("Contraseña") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        trailingIcon = {
                            IconButton(onClick = { verClaveLogin = !verClaveLogin }) {
                                Icon(
                                    if (verClaveLogin) Icons.Default.Info else Icons.Default.Lock,
                                    contentDescription = null
                                )
                            }
                        },
                        visualTransformation = if (verClaveLogin) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Perfil de acceso:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = rolSeleccionadoLogin == "docente",
                            onClick = { rolSeleccionadoLogin = "docente" },
                            label = { Text("Docente") },
                            leadingIcon = { Icon(Icons.Default.AccountBox, contentDescription = null, modifier = Modifier.size(16.dp)) },
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            selected = rolSeleccionadoLogin == "estudiante",
                            onClick = { rolSeleccionadoLogin = "estudiante" },
                            label = { Text("Estudiante") },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(16.dp)) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    // Botón Iniciar Sesión
                    Button(
                        onClick = {
                            if (rolSeleccionadoLogin == "docente") {
                                onIngresarComoDocente()
                            } else {
                                onIngresarComoEstudiante()
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Iniciar Sesión", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }

                    Spacer(modifier = Modifier.height(20.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "O ingresa directamente:",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    // Botones de acceso directo (Requeridos por pruebas automatizadas y accesibilidad rápida)
                    Column(
                        verticalArrangement = Arrangement.spacedBy(10.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            onClick = onIngresarComoDocente,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primaryContainer,
                                contentColor = MaterialTheme.colorScheme.onPrimaryContainer
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.AccountBox, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Ingresar como docente", fontWeight = FontWeight.Bold)
                        }

                        Button(
                            onClick = onIngresarComoEstudiante,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                            shape = RoundedCornerShape(12.dp),
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Ingresar como estudiante", fontWeight = FontWeight.Bold)
                        }
                    }

                } else {
                    // --- VISTA REGISTRARSE ---
                    Text(
                        text = "Crear nueva cuenta",
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )

                    OutlinedTextField(
                        value = nombreRegistro,
                        onValueChange = { nombreRegistro = it },
                        label = { Text("Nombre Completo") },
                        leadingIcon = { Icon(Icons.Default.Edit, contentDescription = null) },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = correoRegistro,
                        onValueChange = { correoRegistro = it },
                        label = { Text("Correo Electrónico") },
                        leadingIcon = { Icon(Icons.Default.AccountCircle, contentDescription = null) },
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = claveRegistro,
                        onValueChange = { claveRegistro = it },
                        label = { Text("Contraseña") },
                        leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
                        trailingIcon = {
                            IconButton(onClick = { verClaveRegistro = !verClaveRegistro }) {
                                Icon(
                                    if (verClaveRegistro) Icons.Default.Info else Icons.Default.Lock,
                                    contentDescription = null
                                )
                            }
                        },
                        visualTransformation = if (verClaveRegistro) VisualTransformation.None else PasswordVisualTransformation(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password),
                        shape = RoundedCornerShape(12.dp),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Selecciona tu perfil:",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.SemiBold,
                        modifier = Modifier.align(Alignment.Start)
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        FilterChip(
                            selected = rolSeleccionadoRegistro == "docente",
                            onClick = { rolSeleccionadoRegistro = "docente" },
                            label = { Text("Docente") },
                            leadingIcon = { Icon(Icons.Default.AccountBox, contentDescription = null, modifier = Modifier.size(16.dp)) },
                            modifier = Modifier.weight(1f)
                        )
                        FilterChip(
                            selected = rolSeleccionadoRegistro == "estudiante",
                            onClick = { rolSeleccionadoRegistro = "estudiante" },
                            label = { Text("Estudiante") },
                            leadingIcon = { Icon(Icons.Default.Person, contentDescription = null, modifier = Modifier.size(16.dp)) },
                            modifier = Modifier.weight(1f)
                        )
                    }

                    Spacer(modifier = Modifier.height(20.dp))

                    Button(
                        onClick = {
                            mensajeRegistroExitoso = true
                            if (rolSeleccionadoRegistro == "docente") {
                                onIngresarComoDocente()
                            } else {
                                onIngresarComoEstudiante()
                            }
                        },
                        shape = RoundedCornerShape(14.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null, modifier = Modifier.size(20.dp))
                        Spacer(modifier = Modifier.width(8.dp))
                        Text("Registrarse", fontWeight = FontWeight.Bold, fontSize = 16.sp)
                    }

                    if (mensajeRegistroExitoso) {
                        Spacer(modifier = Modifier.height(12.dp))
                        Text(
                            text = "Cuenta creada exitosamente",
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}
