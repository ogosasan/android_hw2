package com.example.hw2andr

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.GridItemSpan
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@Composable
fun LazyColumn(
    items: List<String>,
    isLoading: Boolean,
    isError: Boolean,
    onLoadMore: () -> Unit
) {
    Box(modifier = Modifier.fillMaxSize()) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
        ) {
            items(items) { imageUrl ->
                Box(
                    modifier = Modifier
                        .background(Color.LightGray)
                        .padding(4.dp)
                        .fillMaxWidth()
                        .height(200.dp)
                ) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop
                    )

                }

            }

            if (isLoading) {
                item(span = { GridItemSpan(maxLineSpan) }) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }

        }

        if (!isLoading){
            Button(
                onClick = { onLoadMore() },
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .fillMaxWidth()
                    .padding(16.dp),
            ) {
                Text(text = "Show more")
            }
        }




        if (isError){
            AlertDialog(
                onDismissRequest = {onLoadMore()},
                title = { Text(text = "Something went wrong") },
                text = { Text("Try again?") },
                confirmButton = {
                    Button({ onLoadMore() }) {
                        Text("OK")}
                }
            )
        }
    }
}