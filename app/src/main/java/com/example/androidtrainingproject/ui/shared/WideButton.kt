package com.example.androidtrainingproject.ui.shared

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.example.androidtrainingproject.R
import com.example.androidtrainingproject.ui.theme.Purple

@Composable
fun WideButton(modifier: Modifier = Modifier, text: String, enabled: Boolean, onClick: () -> Unit) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = dimensionResource(id = R.dimen.padding_sm_plus))
            .padding(top = dimensionResource(id = R.dimen.padding_l_plus)),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(containerColor = Purple),
        onClick = onClick
    ) {
        Text(text)
    }
}