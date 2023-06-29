package com.example.androidtrainingproject.ui.history

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Divider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.example.androidtrainingproject.GlobalStorage
import com.example.androidtrainingproject.R
import com.example.androidtrainingproject.ui.shared.AppTopBar
import com.example.androidtrainingproject.ui.theme.Gray
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.format.DateTimeFormatter
import java.util.Locale

@Destination
@Composable
fun OrderHistory(destinationsNavigator: DestinationsNavigator) {
    Scaffold(
        topBar = {
            AppTopBar(
                middleText =  stringResource(id = R.string.user_profile),
                destinationsNavigator = destinationsNavigator,
                backIcon = true
            )
        }
    ) { paddingValues ->
        Column(
            Modifier
                .fillMaxSize()
                .paint(
                    painter = painterResource(id = R.drawable.app_background),
                    contentScale = ContentScale.FillBounds
                )
                .padding(top = paddingValues.calculateTopPadding())
                .padding(dimensionResource(id = R.dimen.padding_m))
        ) {
            GlobalStorage.orders.let { orders ->
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                ) {
                    items(orders) { order ->
                        Row {
                            Column {
                                Text(text = order.products.count().toString() + " " + stringResource(id = R.string.order_history_items))
                                Text(text = stringResource(id = R.string.order_history_date) + " " + DateTimeFormatter.ofPattern("dd.MM.yyyy", Locale.ENGLISH).format(order.purchaseDate))
                            }
                            Spacer(modifier = Modifier.weight(0.5f))
                            Text(text = stringResource(id = R.string.total_cost) + String.format("%.2f", order.products.sumOf { it.price }))
                        }
                        Divider(
                            modifier = Modifier.padding(
                                top = dimensionResource(id = R.dimen.padding_sm),
                                bottom = dimensionResource(id = R.dimen.padding_m_plus)
                            ),
                            color = Gray,
                            thickness = dimensionResource(id = R.dimen.divider_size)
                        )
                    }
                }
            }
        }
    }
}