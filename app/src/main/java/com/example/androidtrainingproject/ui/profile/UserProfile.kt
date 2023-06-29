package com.example.androidtrainingproject.ui.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import com.example.androidtrainingproject.GlobalStorage
import com.example.androidtrainingproject.R
import com.example.androidtrainingproject.networking.APIClient
import com.example.androidtrainingproject.ui.destinations.LoginScreenDestination
import com.example.androidtrainingproject.ui.destinations.OrderHistoryDestination
import com.example.androidtrainingproject.ui.shared.AppTopBar
import com.example.androidtrainingproject.ui.shared.WideButton
import com.example.androidtrainingproject.ui.theme.Gray
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun UserProfile(destinationsNavigator: DestinationsNavigator) {
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
            Image(
                painter = painterResource(id = R.drawable.profile_picture),
                contentDescription = null,
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(
                        top = dimensionResource(id = R.dimen.padding_xxl),
                        bottom = dimensionResource(id = R.dimen.padding_m_plus)
                    )
            )
            Text(
                modifier = Modifier.align(Alignment.CenterHorizontally),
                text = GlobalStorage.userEmail.value,
                fontWeight = FontWeight.Bold
            )
            Divider(
                modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.padding_m)),
                color = Gray,
                thickness = dimensionResource(id = R.dimen.divider_size)
            )
            Row(
                modifier = Modifier.clickable { destinationsNavigator.navigate(OrderHistoryDestination) }
            ) {
                Text(text = stringResource(id = R.string.order_history))
                Spacer(modifier = Modifier.weight(0.5f))
                Icon(
                    painter = painterResource(id = R.drawable.ic_chevron_right),
                    contentDescription = null
                )
            }
            WideButton(
                modifier = Modifier.padding(top = dimensionResource(id = R.dimen.padding_xxl)),
                text = stringResource(id = R.string.logout),
                enabled = true,
                onClick = {
                    APIClient.clearJwt()
                    destinationsNavigator.navigate(LoginScreenDestination)
                    GlobalStorage.clearCart()
                }
            )
        }
    }
}