package com.vrashkov.bitfinexcrypto.ui.common

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.vrashkov.bitfinexcrypto.models.mapped.CurrenciesDetailsResult
import com.vrashkov.bitfinexcrypto.util.round
import java.text.NumberFormat
import java.util.*

@Composable
fun InfoBox(
    data: CurrenciesDetailsResult
) {
    Card (modifier = Modifier.fillMaxSize().padding(vertical = 5.dp, horizontal = 10.dp), elevation = 10.dp, shape = RoundedCornerShape(10.dp)){
        Row (modifier = Modifier.padding(15.dp), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically){
            Row (horizontalArrangement = Arrangement.spacedBy(10.dp)){
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(null)// url for the image
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(40.dp).clip(CircleShape)
                )
                Column (verticalArrangement = Arrangement.spacedBy(10.dp)){
                    Text(
                        text = data.label,
                        fontWeight = FontWeight.W700,
                        fontSize = 20.sp
                    )
                    Text(
                        text = data.symbol,
                        fontWeight = FontWeight.W400,
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                }
            }

            Column (verticalArrangement = Arrangement.spacedBy(10.dp), horizontalAlignment = Alignment.End){
                Text(
                    text = "$${NumberFormat.getNumberInstance(Locale.US).format(data.price.toDouble())}",
                    fontWeight = FontWeight.W700,
                    fontSize = 18.sp
                )
                Text(
                    text = "${data.percentageChange.toDouble().round(3)}%",
                    fontWeight = FontWeight.W400,
                    fontSize = 18.sp,
                    color = if (data.percentageChange.toFloat() < 0) {
                        Color.Red
                    } else {
                        Color.Green
                    }
                )
            }
        }
    }
}