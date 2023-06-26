package com.example.androidtrainingproject.ui.shared

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import com.example.androidtrainingproject.R

@Composable
fun SearchBox(
    value: String,
    onChange: (String) -> Unit,
    clear: () -> Unit
) {
    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(50)),
        value = value,
        placeholder = { Text(text = stringResource(id = R.string.search))},
        onValueChange = onChange,
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            if (value != "") {
                IconButton(onClick = clear) {
                    Icon(Icons.Default.Close, contentDescription = null)
                }
            }
        }
    )
}