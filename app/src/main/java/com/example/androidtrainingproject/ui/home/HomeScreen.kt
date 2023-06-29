package com.example.androidtrainingproject.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.RangeSlider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.androidtrainingproject.R
import com.example.androidtrainingproject.ui.destinations.ProductDetailsDestination
import com.example.androidtrainingproject.ui.shared.AppTopBar
import com.example.androidtrainingproject.ui.shared.SearchBox
import com.example.androidtrainingproject.ui.theme.Black
import com.example.androidtrainingproject.ui.theme.Gray
import com.example.androidtrainingproject.ui.theme.LabelText
import com.example.androidtrainingproject.ui.theme.Purple
import com.example.androidtrainingproject.ui.theme.White
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@RootNavGraph(start = true)
@Destination
@Composable
fun Home(destinationsNavigator: DestinationsNavigator) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val searchText by homeViewModel.searchText.collectAsState()
    val activeFiltersCount by homeViewModel.activeFiltersCount.collectAsState()

    val coroutineScope = rememberCoroutineScope()
    val sheetState = rememberModalBottomSheetState()

    var ratingFilter by remember { mutableStateOf(1) }
    var priceRange by remember { mutableStateOf(0f..200f) }
    var checkedCategories by remember { mutableStateOf(mutableListOf<String>()) }

    homeViewModel.allCategories.let { categories ->
        checkedCategories = categories.map { category -> category.name } as MutableList<String>
    }

    fun checkCategory(category: String) {
        checkedCategories = checkedCategories.toMutableList().apply { add(category) }
    }

    fun unCheckCategory(category: String) {
        checkedCategories = checkedCategories.toMutableList().apply { remove(category) }
    }

    Scaffold(
        topBar = {
            AppTopBar(
                middleText =  stringResource(id = R.string.home_top_bar_text),
                destinationsNavigator = destinationsNavigator,
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
                Box {
                    if (activeFiltersCount > 0) {
                        Text(
                            text = "$activeFiltersCount",
                            color = White,
                            modifier = Modifier
                                .align(Alignment.TopEnd)
                                .offset(
                                    x = dimensionResource(id = R.dimen.padding_xs),
                                    y = -dimensionResource(id = R.dimen.padding_xs)
                                )
                                .drawBehind {
                                    drawCircle(
                                        color = Purple,
                                        radius = 24f
                                    )
                                },
                        )
                    }
                    Icon(
                        painter = painterResource(id = R.drawable.ic_filter_list),
                        contentDescription = null,
                        modifier = Modifier
                            .clickable { coroutineScope.launch { sheetState.show() } }
                            .height(dimensionResource(id = R.dimen.icon_large))
                    )
                }
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
                                        destinationsNavigator.navigate(
                                            ProductDetailsDestination(product.id)
                                        )
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

            if (sheetState.isVisible) {
                ModalBottomSheet(
                    modifier = Modifier.fillMaxSize(),
                    onDismissRequest = {
                        coroutineScope.launch {
                            sheetState.hide()
                        }
                    },
                    sheetState = sheetState
                ) {
                    Column(
                        modifier = Modifier.padding(horizontal = dimensionResource(id = R.dimen.padding_l))
                    ) {
                        Row {
                            Icon(
                                Icons.Outlined.Close,
                                contentDescription = null,
                                modifier = Modifier.clickable {
                                    homeViewModel.removeAllFilters()
                                    coroutineScope.launch {
                                        sheetState.hide()
                                    }
                                })
                            Text(
                                text = stringResource(id = R.string.filters),
                                modifier = Modifier.padding(start = dimensionResource(id = R.dimen.padding_sm)),
                                style = MaterialTheme.typography.titleSmall.copy(
                                    color = Black
                                )
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Text(
                                text = stringResource(id = R.string.save),
                                style = MaterialTheme.typography.titleSmall.copy(
                                    color = Purple
                                ),
                                modifier = Modifier.clickable {
                                    homeViewModel.applyFilters(
                                        checkedCategories,
                                        ratingFilter,
                                        priceRange
                                    )

                                    coroutineScope.launch {
                                        sheetState.hide()
                                    }
                                }
                            )
                        }
                        CategoriesAccordion(
                            homeViewModel = homeViewModel,
                            checkedCategories = checkedCategories,
                            ::checkCategory,
                            ::unCheckCategory
                        )
                        Text(
                            text = stringResource(id = R.string.price_range),
                            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_m)),
                            style = MaterialTheme.typography.titleSmall
                        )
                        RangeSlider(
                            value = priceRange,
                            onValueChange = { value ->
                                priceRange = value.start.roundToInt()
                                    .toFloat()..value.endInclusive.roundToInt().toFloat()
                            },
                            valueRange = 0f..200f,
                            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_m)),
                            colors = SliderDefaults.colors(
                                thumbColor = Purple,
                                activeTrackColor = Purple,
                                inactiveTrackColor = Purple,
                                activeTickColor = Purple,
                                inactiveTickColor = Purple,
                                disabledThumbColor = Purple,
                                disabledActiveTrackColor = Purple,
                                disabledActiveTickColor = Purple,
                                disabledInactiveTrackColor = Purple,
                                disabledInactiveTickColor = Purple
                            )
                        )
                        Row(
                            modifier = Modifier
                                .padding(top = dimensionResource(id = R.dimen.padding_sm))
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = "$${priceRange.start}",
                                style = MaterialTheme.typography.titleSmall
                            )
                            Text(
                                text = "$${priceRange.endInclusive}",
                                style = MaterialTheme.typography.titleSmall
                            )
                        }
                        Text(
                            text = stringResource(id = R.string.product_rating),
                            modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_xl)),
                            style = MaterialTheme.typography.titleSmall
                        )
                        LazyRow(
                            modifier = Modifier
                                .padding(top = dimensionResource(id = R.dimen.padding_sm_plus))
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            items(5) {index ->
                                if (index < ratingFilter) {
                                    Icon(
                                        modifier = Modifier
                                            .height(dimensionResource(id = R.dimen.icon_large))
                                            .clickable { ratingFilter = index + 1 },
                                        imageVector = Icons.Outlined.Star,
                                        contentDescription = null,
                                        tint = Purple
                                    )
                                } else {
                                    Image(
                                        modifier = Modifier
                                            .height(dimensionResource(id = R.dimen.icon_large))
                                            .clickable { ratingFilter = index + 1 },
                                        painter = painterResource(id = R.drawable.rating_star_outlined),
                                        contentDescription = null
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}