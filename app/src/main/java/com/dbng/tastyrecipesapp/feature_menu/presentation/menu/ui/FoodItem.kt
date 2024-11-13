package com.dbng.tastyrecipesapp.feature_menu.presentation.menu.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.unit.toSize
import coil.compose.AsyncImage
import com.dbng.tastyrecipesapp.feature_menu.domain.model.MenuItem

@Preview
@Composable
fun FoodItemPreview() {
//    FoodItem(
//        null,
//        onEdit={
//
//        },
//        onItemClick={
//
//        })
}

@Composable
fun FoodItem(
    item: MenuItem,
    onEdit:(String) -> Unit,
    onItemClick:(String,String)->Unit
) {
    Card(
        modifier = Modifier.fillMaxSize(),
        onClick = {
            onItemClick(item.id.toString(),item.name)
        },
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp)

        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                var size by remember { mutableStateOf(Size.Zero) }

                Column(
                    modifier = Modifier
                        .padding(8.dp)
                        .fillMaxWidth(.60F)

                        .onGloballyPositioned {
                            it.size
                                .toSize()
                                .also { size = it }
                        }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Spacer(modifier = Modifier.width(5.dp))

                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    item.name.let {
                        Text(
                            text = it,
                            modifier = Modifier.fillMaxWidth(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    Row(
                        modifier = Modifier
                            .height(40.dp),
                        verticalAlignment = Alignment.Bottom,
                        horizontalArrangement = Arrangement.Center
                    ) {

//                        Button(
//                            modifier = Modifier.fillMaxWidth(),
//                            shape = RoundedCornerShape(5.dp),
//                            onClick = {
//                            }
//                        ) {
                            Text(text = "Detail >>", fontSize = 16.sp)
//                        }
                    }
                }


                Box(
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .fillMaxWidth(.40F),

                    ) {
                    AsyncImage(
                        model = item.imageURL,
                        contentDescription = null,
                        contentScale = ContentScale.FillBounds,
                        modifier = Modifier
                            .fillMaxSize()
                            .size(138.dp)
                    )
                }
            }
        }
    }
}