package com.example.androidtrainingproject.ui.cart

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.androidtrainingproject.GlobalStorage
import com.example.androidtrainingproject.R
import com.example.androidtrainingproject.ui.destinations.HomeDestination
import com.example.androidtrainingproject.ui.shared.AppTopBar
import com.example.androidtrainingproject.ui.shared.WideButton
import com.example.androidtrainingproject.ui.theme.Gray
import com.example.androidtrainingproject.ui.theme.Purple
import com.example.androidtrainingproject.ui.theme.White
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun CartScreen(destinationsNavigator: DestinationsNavigator) {
    val openDialog = remember { mutableStateOf(false)  }

    fun closeDialog() {
        openDialog.value = false
    }

    Scaffold(
        topBar = {
            AppTopBar(
                middleText =  stringResource(id = R.string.cart_top_bar_text),
                destinationsNavigator = destinationsNavigator,
                backIcon = true
            )
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .paint(
                    painter = painterResource(id = R.drawable.app_background),
                    contentScale = ContentScale.FillBounds
                )
                .padding(top = paddingValues.calculateTopPadding())
                .padding(dimensionResource(id = R.dimen.padding_m))
        ) {
            if (GlobalStorage.cartProducts.isEmpty()) {
                Image(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = dimensionResource(id = R.dimen.padding_xxl)),
                    painter = painterResource(id = R.drawable.add_shopping_cart),
                    contentDescription = null,
                    colorFilter = ColorFilter.tint(Purple)
                )
                Text(
                    text = stringResource(id = R.string.empty_cart_message),
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = dimensionResource(id = R.dimen.padding_m))
                )
            } else {
                Column(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                ) {
                    for (i in 0 until GlobalStorage.cartProducts.count()) {
                        val product = GlobalStorage.cartProducts[i]

                        Row(
                            modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_sm_plus))
                        ) {
                            AsyncImage(
                                model = product.image,
                                modifier = Modifier
                                    .width(dimensionResource(id = R.dimen.cart_listing_image_size))
                                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.padding_sm_plus))),
                                contentScale = ContentScale.Fit,
                                alignment = Alignment.Center,
                                contentDescription = stringResource(id = R.string.product_image_description)
                            )
                            Column(
                                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_m))
                            ) {
                                Text(
                                    text = product.title,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = "$${String.format("%.2f", product.price)}",
                                    fontSize = dimensionResource(id = R.dimen.font_size_m).value.sp
                                )
                                Text(
                                    text = stringResource(id = R.string.cart_item_quantity) + " " + "1"
                                )
                            }
                            Spacer(modifier = Modifier.weight(0.5f))
                            Column(
                                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_m))
                            ) {
                                Icon(
                                    modifier = Modifier
                                        .padding(top = dimensionResource(id = R.dimen.padding_m))
                                        .clickable { GlobalStorage.removeProduct(i) },
                                    imageVector = Icons.Filled.Close,
                                    contentDescription = null
                                )
                            }
                        }
                        Divider(color = Gray, thickness = dimensionResource(id = R.dimen.divider_size))
                    }

                    Row(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_m))) {
                        Text(
                            text = stringResource(id = R.string.cart_item_quantity) + " " + GlobalStorage.cartProducts.count().toString(),
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(modifier = Modifier.weight(0.5f))
                        Text(
                            text = stringResource(id = R.string.cart_item_total_text) + " $" + String.format("%.2f", GlobalStorage.cartProducts.sumOf { it.price })
                        )
                    }

                    WideButton(
                        text = stringResource(id = R.string.checkout),
                        enabled = GlobalStorage.cartProducts.isNotEmpty(),
                        onClick = { openDialog.value = true }
                    )
                    if (openDialog.value) {
                        AlertDialog(
                            onDismissRequest = {
                                openDialog.value = false
                            },
                            title = {
                                Text(text = stringResource(id = R.string.checkout_alert_title))
                            },
                            text = {
                                Text(text = stringResource(id = R.string.checkout_alert_text))
                            },
                            dismissButton = {
                                Button(
                                    onClick = { openDialog.value = false },
                                    colors = ButtonDefaults.buttonColors(
                                        contentColor = Purple,
                                        containerColor = White)
                                ) {
                                    Text(text = stringResource(id = R.string.no))
                                }
                            },
                            confirmButton = {
                                Button(
                                    onClick = {
                                        closeDialog()
                                        GlobalStorage.placeOrder()
                                        destinationsNavigator.navigate(HomeDestination)
                                    },
                                    colors = ButtonDefaults.buttonColors(
                                        contentColor = Purple,
                                        containerColor = White)
                                ) {
                                    Text(text = stringResource(id = R.string.yes))
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}
