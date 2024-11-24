package com.example.hw2andr

import androidx.lifecycle.ViewModel
import com.google.gson.JsonParser
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import okhttp3.OkHttpClient
import okhttp3.Request

class PaginationViewModel : ViewModel() {
    private val _items = MutableStateFlow<List<String>>(emptyList())
    val items = _items.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    private val _isError = MutableStateFlow(false)
    val isError = _isError.asStateFlow()

    private var imageNum = 1


    init {
        loadMoreItems()
    }

    private fun fetchJsonField(index: Int): String {
        val client = OkHttpClient()
        val url = "https://xkcd.com/$index/info.0.json"

        return try {
            val request = Request.Builder().url(url).build()
            val response = client.newCall(request).execute()
            val responseBody = response.body?.string()
            if (responseBody != null) {
                val jsonObject = JsonParser.parseString(responseBody).asJsonObject
                jsonObject["img"].asString
            } else {
                ""
            }
        } catch (e: Exception) {
            e.printStackTrace()
            "Error"
        }
    }

    fun loadMoreItems() {
        if (_isLoading.value) return

        _isLoading.value = true


        Thread {
            kotlinx.coroutines.runBlocking {
                var index = imageNum
                for(i in imageNum..imageNum+21) {
                    val url = fetchJsonField(index)
                    if (url.isEmpty()) break
                    if (url == "Error"){
                        _isError.value = true
                        break
                    }
                    _isError.value = false
                    _items.update { it + url }
                    index++
                }
                imageNum+=22
                _isLoading.value = false
            }
        }.start()
    }
}