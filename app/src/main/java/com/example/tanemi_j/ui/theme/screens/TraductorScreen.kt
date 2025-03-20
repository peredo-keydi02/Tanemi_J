package com.example.tanemi_j.ui.theme.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tanemi_j.ui.theme.auth.AuthViewModel
import com.example.tanemi_j.R // Necesario para los recursos como imágenes

@Composable
fun TraductorScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    var inputText by remember { mutableStateOf("") }
    var translatedText by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC2E8FF))
    ) {
        // Botón de retroceso en la esquina superior izquierda
        IconButton(
            onClick = { navController.popBackStack() },
            modifier = Modifier
                .padding(start = 30.dp, top = 35.dp)
                .align(Alignment.TopStart)
        ) {
            Icon(
                painter = painterResource(id = R.drawable.regresar),
                contentDescription = "Volver"
            )
        }

        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Escuchando...",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 20.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Ícono de micrófono
            Icon(
                painter = painterResource(id = R.drawable.microfono),
                contentDescription = "Micrófono",
                modifier = Modifier
                    .size(20.dp)
                    .padding(bottom = 20.dp)
                    .clickable { /* Acción de grabar */ }
            )

            Spacer(modifier = Modifier.height(20.dp))

            Text(
                text = "Traduciendo...",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            // NUEVA SECCIÓN
            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "Introduzca la palabra:",
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 10.dp)
            )

            TextField(
                value = inputText,
                onValueChange = { inputText = it },
                label = { Text("Palabra a traducir") },
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(bottom = 20.dp),
                colors = TextFieldDefaults.colors(focusedIndicatorColor = Color(0xFF1B72B3))
            )

            Button(
                onClick = {
                    if (inputText.isNotBlank()) {
                        authViewModel.originalText.value = inputText
                        translatedText = "Traducción simulada de: $inputText" // Simulación
                        authViewModel.translatedText.value = translatedText
                    } else {
                        errorMessage = "Ingresa una palabra para traducir"
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1C8ADB)),
                modifier = Modifier.padding(bottom = 20.dp)
            ) {
                Text("Traducir", color = Color.White, fontSize = 22.sp)
            }

            if (translatedText.isNotEmpty()) {
                Text(
                    text = "Traducción: $translatedText",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
            }

            if (errorMessage.isNotEmpty()) {
                Text(
                    text = errorMessage,
                    fontSize = 18.sp,
                    color = Color.Red
                )
            }

            Button(
                onClick = {
                    authViewModel.saveTranslation(
                        onSuccess = { errorMessage = "Guardado correctamente" },
                        onError = { error -> errorMessage = error }
                    )
                },
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF4CAF50))
            ) {
                Text("Guardar", color = Color.White, fontSize = 22.sp)
            }
        }
    }
}

