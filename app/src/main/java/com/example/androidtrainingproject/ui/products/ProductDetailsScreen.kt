package com.example.androidtrainingproject.ui.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.androidtrainingproject.R
import com.example.androidtrainingproject.ui.shared.AppTopBar
import com.example.androidtrainingproject.ui.shared.WideButton
import com.example.androidtrainingproject.ui.theme.LabelText
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun ProductDetails(destinationsNavigator: DestinationsNavigator, productId: Number) {
    val productDetailsViewModel: ProductDetailsViewModel = hiltViewModel()
    productDetailsViewModel.getProductById(productId, "*")

    Scaffold(
        topBar = {
            AppTopBar(
                middleText = stringResource(id = R.string.product_top_bar_text),
                destinationsNavigator = destinationsNavigator,
                backIcon = true,
                cartIcon = true
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
                ProductDetailsImage(product)
                ProductDetailsInfo(product)
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
                WideButton(
                    text = stringResource(id = R.string.add_product_button),
                    enabled = true,
                    onClick = {}
                )
            }
        }
    }
}