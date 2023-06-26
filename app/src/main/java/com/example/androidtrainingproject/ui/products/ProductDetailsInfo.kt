package com.example.androidtrainingproject.ui.products

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.androidtrainingproject.R
import com.example.androidtrainingproject.models.ProductResponse
import com.example.androidtrainingproject.ui.theme.Purple

@Composable
fun ProductDetailsInfo(product: ProductResponse) {
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
}