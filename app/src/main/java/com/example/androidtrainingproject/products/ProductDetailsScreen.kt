package com.example.androidtrainingproject.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.androidtrainingproject.R
import com.example.androidtrainingproject.ui.theme.Black
import com.example.androidtrainingproject.ui.theme.Green
import com.example.androidtrainingproject.ui.theme.LabelText
import com.example.androidtrainingproject.ui.theme.Purple
import com.example.androidtrainingproject.ui.theme.Red
import com.example.androidtrainingproject.ui.theme.White

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductDetails() {
    val productDetailsViewModel: ProductDetailsViewModel = hiltViewModel()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = stringResource(id = R.string.product_top_bar_text), maxLines = 1, overflow = TextOverflow.Ellipsis)
                },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.ArrowBack, stringResource(id = R.string.back_arrow_description))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = White,
                    titleContentColor = Black,
                    actionIconContentColor = Black,
                    navigationIconContentColor = Black
                ),
                actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Filled.ShoppingCart, stringResource(id = R.string.shopping_cart_description))
                    }
                }
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
            productDetailsViewModel.productData?.let { product ->
                Box {
                    AsyncImage(
                        model = product.image,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(dimensionResource(id = R.dimen.padding_sm_plus))),
                        contentScale = ContentScale.Fit,
                        alignment = Alignment.Center,
                        contentDescription = stringResource(id = R.string.product_image_description)
                    )
                    Row(
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .padding(
                                top = dimensionResource(id = R.dimen.padding_sm_plus),
                                end = dimensionResource(id = R.dimen.padding_sm_plus)
                            )
                            .clip(shape = RoundedCornerShape(50))
                            .background(White)
                            .padding(
                                horizontal = dimensionResource(id = R.dimen.padding_sm)
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        if (product.stock.toInt() > 0) {
                            Icon(
                                modifier = Modifier
                                    .height(dimensionResource(id = R.dimen.icon_normal))
                                    .padding(end = dimensionResource(id = R.dimen.padding_sm)),
                                imageVector = Icons.Outlined.CheckCircle,
                                contentDescription = null,
                                tint = Green
                            )
                            Text(
                                text = stringResource(id = R.string.product_in_stock),
                                fontSize = dimensionResource(id = R.dimen.font_size_sm_plus).value.sp
                            )
                        } else {
                            Icon(
                                modifier = Modifier
                                    .height(dimensionResource(id = R.dimen.icon_normal))
                                    .padding(end = dimensionResource(id = R.dimen.padding_sm)),
                                imageVector = Icons.Outlined.Close,
                                contentDescription = null,
                                tint = Red
                            )
                            Text(
                                text = stringResource(id = R.string.product_out_of_stock),
                                fontSize = dimensionResource(id = R.dimen.font_size_sm_plus).value.sp,
                                color = Red
                            )
                        }
                        
                    }
                }
                Row(
                    modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_sm_plus)),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = product.title,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.weight(0.5f))
                    Text(text = product.rating.toString())
                    for (i in 0 until product.rating.toInt()) {
                        Icon(
                            modifier = Modifier.height(dimensionResource(id = R.dimen.icon_small)),
                            imageVector = Icons.Outlined.Star,
                            contentDescription = null,
                            tint = Purple
                        )
                    }
                    for (i in product.rating.toInt() until 5) {
                        Image(
                            modifier = Modifier.height(dimensionResource(id = R.dimen.icon_small)),
                            painter = painterResource(id = R.drawable.rating_star_outlined),
                            contentDescription = null
                        )
                    }
                }
                Text(
                    text = stringResource(id = R.string.product_category_label) + " " + product.category,
                    fontSize = dimensionResource(id = R.dimen.font_size_sm).value.sp,
                    color = LabelText
                )
                Text(
                    modifier = Modifier
                        .padding(
                            top = dimensionResource(id = R.dimen.padding_m),
                            bottom = dimensionResource(id = R.dimen.padding_sm)
                        ),
                    text = product.description,
                    fontSize = dimensionResource(id = R.dimen.font_size_m).value.sp
                )
                Text(
                    text = "$${String.format("%.2f", product.price)}",
                    fontSize = dimensionResource(id = R.dimen.font_size_xl).value.sp,
                    fontWeight = FontWeight.Bold
                )
                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = dimensionResource(id = R.dimen.padding_xxl))
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_sm)),
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(containerColor = Purple)
                ) {
                    Text(stringResource(id = R.string.add_product_button))
                }
            }
        }
    }
}