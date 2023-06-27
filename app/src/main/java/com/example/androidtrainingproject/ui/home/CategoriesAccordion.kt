package com.example.androidtrainingproject.ui.home

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color.Companion.Transparent
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.androidtrainingproject.R
import com.example.androidtrainingproject.ui.theme.Black
import com.example.androidtrainingproject.ui.theme.Purple

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesAccordion(
    homeViewModel: HomeViewModel,
    checkedCategories: List<String?>,
    checkCategory: (String) -> Unit,
    unCheckCategory: (String) -> Unit
) {
    var expanded by remember { mutableStateOf(false) }
    val rotateState = animateFloatAsState(targetValue = if (expanded) 180f else 0f)

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.padding_xl))
    ) {
        Card(
            onClick = { expanded = !expanded },
            colors = CardDefaults.cardColors(
                containerColor = Transparent,
                contentColor = Black
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(id = R.string.product_category_label),
                    modifier = Modifier.fillMaxWidth(0.90f),
                    style = MaterialTheme.typography.titleSmall
                )
                Icon(
                    modifier = Modifier.rotate(rotateState.value),
                    painter = painterResource(id = R.drawable.expand_more),
                    contentDescription = null
                )
            }
        }

        AnimatedVisibility(visible = expanded) {
            Column(modifier = Modifier.fillMaxWidth()) {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = dimensionResource(id = R.dimen.padding_sm))
                ) {
                    items(homeViewModel.allCategories.size) { index ->
                        val category = homeViewModel.allCategories[index]
                        val categoryName = category.name

                        Row(
                            modifier = Modifier
                                .padding(top = dimensionResource(id = R.dimen.padding_m))
                                .height(dimensionResource(id = R.dimen.category_row_height))
                                .clickable {
                                    if (checkedCategories.contains(category.name)) {
                                        unCheckCategory(categoryName)
                                    } else {
                                        checkCategory(categoryName)
                                    }
                                }
                        ) {
                            Text(
                                text = categoryName,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontWeight = FontWeight.Medium,
                                )
                            )
                            Spacer(modifier = Modifier.weight(1f))
                            Box {
                                if (checkedCategories.contains(category.name)) {
                                    Icon(
                                        Icons.Filled.Check,
                                        contentDescription = null,
                                        tint = Purple
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