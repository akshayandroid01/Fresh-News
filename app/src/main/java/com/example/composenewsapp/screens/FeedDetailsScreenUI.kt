package com.example.composenewsapp.screens

import android.widget.TextView
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.text.HtmlCompat
import androidx.navigation.NavHostController
import coil.compose.SubcomposeAsyncImage
import com.example.composenewsapp.R
import com.example.composenewsapp.utills.AppConstants
import com.example.composenewsapp.utills.getDateFormat
import com.example.composenewsapp.viewModels.FeedViewModel


@Composable
fun FeedDetailUI(
    feedViewModel: FeedViewModel,
    navController: NavHostController
) {

    val item by feedViewModel.feed.observeAsState()

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
    ){
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "")
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

            Column {
                SubcomposeAsyncImage(
                    model = item?.enclosure?.link,
                    contentDescription = AppConstants.Image,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(height = 200.dp)
                        .fillMaxWidth(),
                    loading = {
                        Box(modifier = Modifier.size(30.dp), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                        }
                    },
                    error = {
                        Image(painter = painterResource(id = R.drawable.placeholder),
                            contentDescription = AppConstants.Image,
                            modifier = Modifier
                                .fillMaxSize(),
                        )
                    },
                    contentScale = ContentScale.FillBounds
                )

                Text(
                    text = item?.title ?: "",
                    maxLines = 2,
                    modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp),
                    style = TextStyle(fontSize = 15.sp),
                    fontWeight = FontWeight.Bold
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
                        text = getDateFormat(item?.pubDate) ?: "",
                        maxLines = 1,
                        color = Color.Black,
                        style = TextStyle(fontSize = 10.sp),
                        fontWeight = FontWeight.Bold
                    )

                }

                Spacer(modifier = Modifier.height(10.dp))

                if (item?.categories?.isEmpty() == false && item?.categories!![0] == AppConstants.Advertorial) {
                    AndroidView(
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp),
                        factory = { context -> TextView(context) },
                        update = { it.text = HtmlCompat.fromHtml(item?.description ?: "",
                            HtmlCompat.FROM_HTML_MODE_COMPACT) }
                    )
                } else {
                    Text(
                        text = item?.description ?: "",
                        modifier = Modifier.padding(vertical = 5.dp, horizontal = 5.dp),
                        style = TextStyle(fontSize = 12.sp),
                    )
                }


                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}