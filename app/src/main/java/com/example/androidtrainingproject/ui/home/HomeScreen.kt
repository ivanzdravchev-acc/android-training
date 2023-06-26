package com.example.androidtrainingproject.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.androidtrainingproject.R
import com.example.androidtrainingproject.ui.shared.AppTopBar
import com.example.androidtrainingproject.ui.shared.SearchBox
import com.example.androidtrainingproject.ui.theme.Gray
import com.example.androidtrainingproject.ui.theme.LabelText
import com.example.androidtrainingproject.ui.theme.Purple

@Composable
fun Home(navController: NavController) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val searchText by homeViewModel.searchText.collectAsState()

    Scaffold(
        topBar = {
            AppTopBar(
                middleText =  stringResource(id = R.string.home_top_bar_text),
                navController = navController,
                accountIcon = true,
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
            SearchBox(
                value = searchText,
                onChange = { value -> homeViewModel.updateSearchText(value) },
                clear = { homeViewModel.clearSearchText() }
            )
            Row(
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_m)),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.home_new_products),
                    fontSize = dimensionResource(id = R.dimen.font_size_l).value.sp
                )
                Spacer(modifier = Modifier.weight(0.5f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_filter_list),
                    contentDescription = null,
                    modifier = Modifier.height(dimensionResource(id = R.dimen.icon_large))
                )
            }

            if (homeViewModel.isLoading) {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator()
                }
            }

            homeViewModel.filteredProducts.let { products ->
                LazyColumn(modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)) {
                    items(products) { product ->
                        Row(
                            modifier = Modifier
                                .padding(vertical = dimensionResource(id = R.dimen.padding_sm_plus)),

                        ) {
                            AsyncImage(
                                model = product.image,
                                modifier = Modifier
                                    .width(dimensionResource(id = R.dimen.home_image_size))
                                    .clip(RoundedCornerShape(dimensionResource(id = R.dimen.padding_sm_plus)))
                                    .clickable(onClick = {
                                        navController.navigate("productDetails/${product.id}")
                                    }),
                                contentScale = ContentScale.Fit,
                                alignment = Alignment.Center,
                                contentDescription = stringResource(id = R.string.product_image_description)
                            )
                            Column(
                                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_m))
                            ) {
                                Text(
                                    text = stringResource(id = R.string.product_category_label) + " " + product.category,
                                    fontSize = dimensionResource(id = R.dimen.font_size_sm).value.sp,
                                    color = LabelText
                                )
                                Text(
                                    text = product.title,
                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = product.short_description,
                                    fontSize = dimensionResource(id = R.dimen.font_size_sm).value.sp,
                                    lineHeight = dimensionResource(id = R.dimen.home_custom_line_height).value.sp,
                                    color = LabelText
                                )
                                Row(
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(text = product.rating.toString())
                                    for (i in 0 until product.rating.toInt()) {
                                        Icon(
                                            modifier = Modifier.height(dimensionResource(id = R.dimen.icon_small)),
                                            imageVector = Icons.Outlined.Star,
                                            contentDescription = null,
                                            tint = Purple
                                        )
                                    }
                                }
                                Text(
                                    text = "$${String.format("%.2f", product.price)}",
                                    fontSize = dimensionResource(id = R.dimen.font_size_m).value.sp
                                )
                            }
                        }
                        Divider(color = Gray, thickness = dimensionResource(id = R.dimen.divider_size))
                    }
                }
            }
        }
    }
}