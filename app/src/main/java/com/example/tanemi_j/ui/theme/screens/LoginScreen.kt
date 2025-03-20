package com.example.tanemi_j.ui.theme.screens

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.tanemi_j.ui.theme.auth.AuthViewModel
import com.example.tanemi_j.ui.theme.auth.LoginResult
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavHostController, authViewModel: AuthViewModel) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var termsChecked by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val loginState by authViewModel.loginState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFC2E8FF)),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Inicio de sesión", fontSize = 32.sp, color = Color.Black)
        Spacer(modifier = Modifier.height(16.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .background(Color(0xFF90CAF9), shape = RoundedCornerShape(10.dp))
                .border(3.dp, Color.White, RoundedCornerShape(10.dp))
                .padding(20.dp)
        ) {
            Column {
                LoginInputField("Correo Electrónico", email) { email = it }
                LoginInputField("Contraseña", password, isPassword = true) { password = it }

                Spacer(modifier = Modifier.height(8.dp))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = termsChecked, onCheckedChange = { termsChecked = it })
                    Text(text = "Términos y condiciones", fontSize = 18.sp)
                }
            }
        }

        if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = Color.Red, fontSize = 18.sp, modifier = Modifier.padding(top = 8.dp))
        }

        Button(
            onClick = {
                if (email.isNotBlank()) {
                    if (password.isNotBlank()) {
                        if(esEmailValido(email)){
                            if (termsChecked) {
                                authViewModel.loginUser(
                                    email,
                                    password,
                                    onSuccess = {
                                        navController.navigate("menu") {
                                            popUpTo("login") { inclusive = true }
                                        }
                                    },
                                    onError = { error ->
                                        errorMessage = error
                                    }
                                )
                            } else {
                                errorMessage = "Por favor, acepte los términos y condiciones."
                            }
                        }else {
                            errorMessage = "Por favor, introduzca un correo electrónico válido."
                        }

                    } else {
                        errorMessage = "Por favor, introduzca su contraseña."
                    }
                } else {
                    errorMessage = "Por favor, introduzca su correo electrónico."
                }
            },
            colors = ButtonDefaults.buttonColors(Color(0xFF42A5F5)),
            modifier = Modifier.padding(top = 15.dp)
        ) {
            Text(text = "Iniciar sesión", fontSize = 22.sp)
        }


        TextButton(onClick = { navController.navigate("registro") }) {
            Text(text = "Registro", fontSize = 22.sp, color = Color(0xFF42A5F5), modifier = Modifier.padding(top = 15.dp))
        }

        TextButton(onClick = { navController.navigate("recuperarcontrasena") }) {
            Text(text = "¿Olvidastre tu contraseña?", fontSize = 20.sp, color = Color(0xFF42A5F5), modifier = Modifier.padding(top = 15.dp))
        }
    }

}

@Composable
fun LoginInputField(
    label: String,
    value: String,
    keyboardType: KeyboardType = KeyboardType.Text,
    isPassword: Boolean = false,
    onValueChange: (String) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()) {
        Text(text = label, fontSize = 22.sp, color = Color.Black, modifier = Modifier.padding(top = 10.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 7.dp)
                .background(Color.White, shape = RoundedCornerShape(10.dp)) // Solo 1 fondo
                .padding(horizontal = 8.dp, vertical = 2.dp) // Padding interno
        ) {
            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                textStyle = TextStyle(fontSize = 20.sp, color = Color.Black),
                keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                visualTransformation = if (isPassword) PasswordVisualTransformation() else VisualTransformation.None,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            )
        }
    }
}
