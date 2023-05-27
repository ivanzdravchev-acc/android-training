package com.example.androidtrainingproject.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidtrainingproject.R


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navigateToProductDetails: () -> Unit = {}) {
    val usernameField = remember { mutableStateOf("") }
    val passwordField = remember { mutableStateOf("") }

    val isPasswordVisible = remember { mutableStateOf(false) }

    val loginViewModel: LoginViewModel = hiltViewModel()

    val passwordIcon = isPasswordVisible.let {
        if (isPasswordVisible.value) {
            R.drawable.ic_visibility_on
        } else {
            R.drawable.ic_visibility_off
        }
    }

    val keyboardController = LocalSoftwareKeyboardController.current

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

        if (loginViewModel.loginFailure) {
            Toast.makeText(LocalContext.current, "Invalid login credentials", Toast.LENGTH_SHORT).show()
            loginViewModel.loginFailure = false
        }

        Button(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            enabled = !loginViewModel.isLoading,
            onClick = {
                loginViewModel.login(usernameField.value, passwordField.value, navigateToProductDetails)
                passwordField.value = ""

                keyboardController?.hide()
            }
        ) {
            Text("Log in")
        }
    }
}
