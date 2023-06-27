package com.example.androidtrainingproject.ui.shared

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.navigation.NavController
import com.example.androidtrainingproject.R
import com.example.androidtrainingproject.ui.theme.Black
import com.example.androidtrainingproject.ui.theme.White
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    middleText: String,
    destinationsNavigator: DestinationsNavigator,
    backIcon: Boolean? = false,
    accountIcon: Boolean? = false,
    cartIcon: Boolean? = false
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = middleText, maxLines = 1, overflow = TextOverflow.Ellipsis)
        },
        navigationIcon = {
            if (backIcon == true) {
                IconButton(onClick = { destinationsNavigator.navigateUp() }) {
                    Icon(Icons.Filled.ArrowBack, stringResource(id = R.string.back_arrow_description))
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = White,
            titleContentColor = Black,
            actionIconContentColor = Black,
            navigationIconContentColor = Black
        ),
        actions = {
            if (accountIcon == true) {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.AccountCircle, stringResource(id = R.string.account_box_description))
                }
            }
            if (cartIcon == true) {
                IconButton(onClick = {}) {
                    Icon(Icons.Filled.ShoppingCart, stringResource(id = R.string.shopping_cart_description))
                }
            }
        }
    )
}