@file:Suppress("OPT_IN_IS_NOT_ENABLED")

package com.example.composenewsapp.screens

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.example.composenewsapp.R
import com.example.composenewsapp.utills.Utils
import com.example.composenewsapp.dataModels.ItemsItem
import com.example.composenewsapp.utills.AppConstants
import com.example.composenewsapp.utills.getDateFormat
import com.example.composenewsapp.viewModels.FeedViewModel
import java.util.*

@Composable
fun FeedsScreen(
    feedViewModel: FeedViewModel,
    endPoint: String?, navController: NavHostController,
    onItemSelect: (ItemsItem?) -> Unit
) {

    val feeds by feedViewModel.feeds.observeAsState(null)

    AnimatedVisibility(
        visible = true,
        modifier = Modifier.fillMaxSize(),
        enter = slideInHorizontally(
            initialOffsetX = { it },  // it == fullWidth
            animationSpec = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        ),
        exit = slideOutHorizontally(
            targetOffsetX = { it },  // it == fullWidth
            animationSpec = tween(
                durationMillis = 1000,
                easing = LinearEasing
            )
        )
    ) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "$endPoint ${Utils.Feeds}")
                    },
                    modifier = Modifier.fillMaxWidth(),
                    navigationIcon = if (navController.previousBackStackEntry != null) {
                        {
                            IconButton(
                                onClick = { navController.navigateUp() }) {
                                Icon(
                                    imageVector = Icons.Filled.ArrowBack,
                                    contentDescription = AppConstants.Back
                                )
                            }
                        }
                    } else {
                        null
                    })
            }
        ) {
            if (feeds != null && (feeds?.status?.lowercase() == AppConstants.Ok) && (feeds?.items?.size
                    ?: 0) > 0
            ) {
                LazyColumn(modifier = Modifier.padding(10.dp)) {
                    items(feeds?.items?.size ?: 0) { index ->
                        FeedItem(item = feeds?.items?.get(index)!!) {
                            feedViewModel.selectedItem(feeds?.items!![index])
                            onItemSelect.invoke(feeds?.items!![index])
                        }
                    }
                }
            } else {

                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    if (feeds == null) {
                        Box(modifier = Modifier.size(30.dp), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    } else if (feeds?.status?.lowercase() == AppConstants.Error) {
                        Text(text = feeds?.message ?: "")
                    } else if (feeds?.items?.isEmpty() == true) {
                        Text(text = LocalContext.current.resources.getString(R.string.txt_no_feed))
                    }
                }

            }

        }
    }

}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FeedItem(item: ItemsItem, onTap: () -> Unit) {

    Column {
        Card(
            onClick = onTap,
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .padding(5.dp),
            elevation = 4.dp,
            shape = RoundedCornerShape(20.dp)
        ) {
            Column {
                SubcomposeAsyncImage(
                    model = item.enclosure?.link,
                    contentDescription = AppConstants.Image,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 150.dp)
                        .clip(RoundedCornerShape(10.dp)),
                    loading = {
                        Box(modifier = Modifier.size(30.dp),
                            contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    },
                    error = {
                            Image(painter = painterResource(id = R.drawable.placeholder),
                                contentDescription = AppConstants.Image,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                    },
                    contentScale = ContentScale.Crop
                )

                Text(
                    text = item.title ?: "",
                    maxLines = 2,
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp),
                    style = TextStyle(fontSize = 12.sp),
                )

                Row(
                    modifier = Modifier.padding(horizontal = 5.dp)
                ) {
                    Text(
                        text = LocalContext.current.resources.getString(R.string.txt_publish),
                        maxLines = 1,
                        color = Color.Gray,
                        style = TextStyle(fontSize = 10.sp),
                    )
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = getDateFormat(item.pubDate) ?: "",
                        maxLines = 1,
                        color = Color.Black,
                        style = TextStyle(fontSize = 10.sp),
                        fontWeight = FontWeight.Bold
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))
            }
        }

    }
}
