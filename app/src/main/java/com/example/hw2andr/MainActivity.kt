package com.example.hw2andr

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
                PaginationScreen()
        }
    }
}
@Composable
fun PaginationScreen(viewModel: PaginationViewModel = androidx.lifecycle.viewmodel.compose.viewModel()) {
    val items by viewModel.items.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val isError by viewModel.isError.collectAsState()

    LazyColumn(
        items = items,
        isLoading = isLoading,
        isError = isError,
        onLoadMore = { viewModel.loadMoreItems() }
    )
}
