@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.example.composenewsapp.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.example.composenewsapp.R
import com.example.composenewsapp.utills.AppConstants
import com.example.composenewsapp.utills.Utils
import java.util.*

@Composable
fun HomeScreenListView(onNavigateToFeed: (String) -> Unit) {
    val list = LocalContext.current.resources.getStringArray(R.array.categories_array)

    val colorList = listOf(
        Color.Green, Color.White, Color.Blue, Color.Cyan, Color.Red,
        Color.Magenta, Color.Yellow
    )

    val rnd = Random()

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(text = Utils.Categories)
            }, modifier = Modifier.fillMaxWidth())
        }
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(10.dp)
        ) {
            items(list.size) {
                val color = colorList[rnd.nextInt(colorList.size)]
                HomeScreenListItem(title = list[it], textColor = color) {
                    onNavigateToFeed.invoke(list[it])
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
private fun HomeScreenListItem(
    title: String,
    textColor: Color,
    onSelectCategory: () -> Unit
) {


    Card(
        onClick = onSelectCategory,
        modifier = Modifier
            .padding(8.dp)
            .height(150.dp),
        elevation = 6.dp
    ) {
        ConstraintLayout(
            Modifier
                .fillMaxSize()
        ) {
            val (upperLayout, image) = createRefs()
            Image(
                painter = painterResource(id = getBackCoverImage(title = title)),
                contentDescription = AppConstants.Image,
                modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(image) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                contentScale = ContentScale.FillBounds
            )
            Column(
                Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.5f))
                    .constrainAs(upperLayout) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    },
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                Text(
                    text = title,
                    modifier = Modifier.padding(4.dp),
                    color = textColor,
                    fontFamily = FontFamily.Monospace,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@Composable
private fun getBackCoverImage(title: String): Int {
    return when (title) {
        "Education" -> R.drawable.education
        "Astrology" -> R.drawable.astrologyt
        "Books" -> R.drawable.books
        "Business" -> R.drawable.business
        "Car Bike" -> R.drawable.carbike
        "Cities" -> R.drawable.city
        "Art-Culture" -> R.drawable.art
        "Lifestyle" -> R.drawable.lifestyle
        "Elections" -> R.drawable.election
        else -> -1
    }

}