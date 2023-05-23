package com.example.androidtrainingproject.products

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage

@Composable
fun ProductDetails(productId: Number) {
    val productDetailsViewModel: ProductDetailsViewModel = hiltViewModel()
    
    productDetailsViewModel.getProductById(productId)
    
    Column(
        Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = "Item",
            textAlign = TextAlign.Center
        )

        productDetailsViewModel.productData?.let { product ->
            AsyncImage(
                model = product.image,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
                    .clip(RoundedCornerShape(10.dp)),
                contentScale = ContentScale.Fit,
                alignment = Alignment.Center,
                contentDescription = "Item Image"
            )
            Text(
                modifier = Modifier.padding(bottom = 10.dp),
                text = product.description,
                fontSize = 16.sp
            )
            Text(
                text = "$${product.price}.00",
                fontSize = 30.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Button(
            modifier = Modifier
                .padding(top = 50.dp)
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            onClick = {},
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF47337A))
        ) {
            Text("Add to cart")
        }
    }
}