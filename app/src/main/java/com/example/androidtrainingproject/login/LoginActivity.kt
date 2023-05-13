package com.example.androidtrainingproject.login

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtrainingproject.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(navigateToProductDetails: () -> Unit = {}) {
    val usernameField = remember { mutableStateOf("") }
    val passwordField = remember { mutableStateOf("") }

    val isPasswordVisible = remember { mutableStateOf(false) }
    val showWrongPasswordMessage = remember { mutableStateOf(false) }

    val hardcodedUsername = "a"
    val hardcodedPassword = "1234"

    val passwordIcon = isPasswordVisible.let {
        if (isPasswordVisible.value) {
            R.drawable.ic_visibility_on;
        } else {
            R.drawable.ic_visibility_off
        }
    }

    Column(modifier = Modifier.padding(10.dp)) {
        Image(
            painter = painterResource(id = R.drawable.img_ethereal_artefacts_logo),
            contentDescription = "ethereal artefacts logo"
        )
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = "Log in",
            fontSize = 26.sp
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            value = usernameField.value,
            label = { Text(text = "Email")},
            onValueChange = {
                    newValue -> usernameField.value = newValue
            }
        )
        OutlinedTextField(
            modifier = Modifier
                .padding(bottom = 10.dp)
                .fillMaxWidth(),
            value = passwordField.value,
            label = { Text(text = "Password")},
            onValueChange = {
                    newValue -> passwordField.value = newValue
            },
            visualTransformation = if (isPasswordVisible.value) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {
                    isPasswordVisible.value = !isPasswordVisible.value
                }) {
                    Image(
                        modifier = Modifier
                            .height(36.dp)
                            .width(32.dp),
                        painter = painterResource(id = passwordIcon),
                        contentDescription = "toggle password visibility"
                    )
                }
            },
        )

        if (showWrongPasswordMessage.value) {
            Text("Invalid login credentials", color = Color.Red)
        }

        Button(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            onClick = {
                if (usernameField.value == hardcodedUsername && passwordField.value == hardcodedPassword) {
                    navigateToProductDetails()
                } else {
                    showWrongPasswordMessage.value = true;
                }
                passwordField.value = ""
            }
        ) {
            Text("Log in")
        }
    }
}
