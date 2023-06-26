package com.example.androidtrainingproject.ui.products

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.CheckCircle
import androidx.compose.material.icons.outlined.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.androidtrainingproject.R
import com.example.androidtrainingproject.models.ProductResponse
import com.example.androidtrainingproject.ui.theme.Green
import com.example.androidtrainingproject.ui.theme.Red
import com.example.androidtrainingproject.ui.theme.White

@Composable
fun ProductDetailsImage(product: ProductResponse) {
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
                        .height(dimensionResource(id = R.dimen.icon_large))
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
                        .height(dimensionResource(id = R.dimen.icon_large))
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
}
