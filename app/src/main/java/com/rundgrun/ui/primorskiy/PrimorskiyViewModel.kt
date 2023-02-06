package com.rundgrun.ui.primorskiy

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.GsonBuilder
import com.rundgrun.ui.models.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.BufferedInputStream
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import javax.net.ssl.HttpsURLConnection

class PrimorskiyViewModel : ViewModel() {
    val primorskiyPath =
        "https://www.avito.ru/js/1/map/items?categoryId=24&locationId=653240&priceMin=25000&priceMax=35000&correctorMode=0&page=1&map=eyJzZWFyY2hBcmVhIjp7ImxhdEJvdHRvbSI6NTkuOTk1NTg5NDU4MTk2MzUsImxhdFRvcCI6NjAuMDAyMDI2OTkxNzI1OTEsImxvbkxlZnQiOjMwLjI5OTMwMDQ4MTU1MzIwNywibG9uUmlnaHQiOjMwLjMxMDUwMTM4NjM5OTM5OH0sInpvb20iOjE2fQ%3D%3D&params%5B201%5D=1060&params%5B504%5D=5256&verticalCategoryId=1&rootCategoryId=4&localPriority=0&searchArea%5BlatBottom%5D=59.99558945819635&searchArea%5BlonLeft%5D=30.299300481553207&searchArea%5BlatTop%5D=60.00202699172591&searchArea%5BlonRight%5D=30.310501386399398&viewPort%5Bwidth%5D=522.4000244140625&viewPort%5Bheight%5D=600&limit=10&countAndItemsOnly=1"
    val prefix = "https://www.avito.ru/"

    private val _text = MutableLiveData<String>()
    val text: LiveData<String> = _text

    fun readData() {
        viewModelScope.launch(Dispatchers.IO) {
            val url = URL(primorskiyPath)
            val urlConnection: HttpsURLConnection = url.openConnection() as HttpsURLConnection
            try {
                val input = BufferedInputStream(urlConnection.inputStream)
                val response = StringBuilder()
                input.bufferedReader().forEachLine { response.append(it) }
                val gson = GsonBuilder().create()
                val resp: Response = gson.fromJson(response.toString(), Response::class.java)
                withContext(Dispatchers.Main) {
                    _text.value = prefix + resp.items[0].urlPath
                }
            } finally {
                urlConnection.disconnect()
            }
        }
    }

    fun getLink() {

    }
}