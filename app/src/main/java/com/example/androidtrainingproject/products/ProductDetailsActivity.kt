package com.example.androidtrainingproject.products

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.androidtrainingproject.R

@Composable
fun ProductDetails() {
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
        Image(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
                .width(200.dp)
                .border(BorderStroke(1.dp, Color.Black)),
            alignment = Alignment.Center,
            painter = painterResource(id = R.drawable.ic_android_logo),
            contentDescription = "Item Image"
        )
        Text(
            modifier = Modifier.padding(bottom = 10.dp),
            text = "Indulge in a heavenly tea experience with our Stargazer's Tea Set, featuring a constellation-themed teapot and matching teacups. Crafted from fine porcelain, this elegant set will transport you to the cosmos with every sip."
        )
        Text(
            text = "$90.00",
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold
        )
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