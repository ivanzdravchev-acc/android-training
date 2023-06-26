package com.example.androidtrainingproject.ui.login

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidtrainingproject.R
import com.example.androidtrainingproject.ui.shared.WideButton
import com.example.androidtrainingproject.ui.theme.ErrorDarkRed
import com.example.androidtrainingproject.ui.theme.Purple


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun LoginScreen(navigateToHome: () -> Unit = {}) {
    var usernameField by remember { mutableStateOf("") }
    var passwordField by remember { mutableStateOf("") }

    var markInputsAsWrong by remember { mutableStateOf(false) }
    var isPasswordVisible by remember { mutableStateOf(false) }

    val loginViewModel: LoginViewModel = hiltViewModel()
    val loginFailure by loginViewModel.loginFailure.collectAsState()

    val passwordIcon = isPasswordVisible.let {
        if (isPasswordVisible) {
            R.drawable.ic_visibility_on
        } else {
            R.drawable.ic_visibility_off
        }
    }

    val context = LocalContext.current
    val loginFailureMessage = stringResource(id = R.string.login_failure)
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(loginFailure) {
        if (loginFailure == true) {
            Toast.makeText(context, loginFailureMessage, Toast.LENGTH_SHORT).show()
            markInputsAsWrong = true
        }
    }

    Column(modifier = Modifier
        .fillMaxSize()
        .paint(
            painter = painterResource(id = R.drawable.login_background),
            contentScale = ContentScale.FillBounds
        )
        .padding(dimensionResource(id = R.dimen.padding_m_plus))
    ) {
        Image(
            painter = painterResource(id = R.drawable.img_ethereal_artefacts_logo),
            contentDescription = stringResource(id = R.string.login_logo_description),
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .padding(
                    top = dimensionResource(id = R.dimen.padding_xxl),
                    bottom = dimensionResource(id = R.dimen.padding_xxl)
                )
        )
        Text(
            modifier = Modifier.padding(bottom = dimensionResource(id = R.dimen.padding_sm)),
            text = stringResource(id = R.string.login_header),
            fontSize = dimensionResource(id = R.dimen.font_size_xxl).value.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = FontFamily.SansSerif,
            color = Purple,
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = dimensionResource(id = R.dimen.padding_sm)),
            value = usernameField,
            label = { Text(text = stringResource(id = R.string.label_email)) },
            colors = TextFieldDefaults.colors(
                errorIndicatorColor = ErrorDarkRed,
                errorContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
            ),
            isError = markInputsAsWrong,
            onValueChange = { newValue -> usernameField = newValue }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = passwordField,
            label = { Text(text = stringResource(id = R.string.label_password)) },
            colors = TextFieldDefaults.colors(
                errorIndicatorColor = ErrorDarkRed,
                errorContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedContainerColor = Color.Transparent,
            ),
            isError = markInputsAsWrong,
            onValueChange = { newValue -> passwordField = newValue },
            visualTransformation = if (isPasswordVisible) VisualTransformation.None else PasswordVisualTransformation(),
            trailingIcon = {
                IconButton(onClick = {
                    isPasswordVisible = !isPasswordVisible
                }) {
                    Image(
                        modifier = Modifier
                            .height(dimensionResource(id = R.dimen.icon_large))
                            .width(dimensionResource(id = R.dimen.icon_large)),
                        painter = painterResource(id = passwordIcon),
                        contentDescription = stringResource(id = R.string.toggle_password_visibility)
                    )
                }
            },
        )

        WideButton(
            text = stringResource(id = R.string.login_button),
            enabled = !loginViewModel.isLoading,
            onClick = {
                loginViewModel.login(usernameField, passwordField, navigateToHome)
                markInputsAsWrong = false
                passwordField = ""

                keyboardController?.hide()
        })
    }
}
