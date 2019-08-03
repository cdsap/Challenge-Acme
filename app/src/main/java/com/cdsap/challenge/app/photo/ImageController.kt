package com.cdsap.challenge.app.photo

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.ImageView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class ImageController {
    private val cache: LRUImageCache

    init {
        val maxMemory = (Runtime.getRuntime().maxMemory() / 1024).toInt()
        val cacheSize = maxMemory / 4
        Log.e("ImageController", "Size cache: $cacheSize")
        cache = LRUImageCache(cacheSize)
    }

    fun loadImage(image: ImageView, url: String) {
        if (cache.get(url) != null) {
            image.setImageBitmap(cache.get(url))
        } else {
            GlobalScope.launch {
                val a = getImage(url)
                withContext(Dispatchers.Main) {
                    if (a != null) {
                        cache.put(url, a)
                        image.setImageBitmap(a)
                    }
                }
            }
        }
    }

    private fun getImage(url: String): Bitmap? {
        return try {
            val options = BitmapFactory.Options()
            options.inJustDecodeBounds = true
            options.inSampleSize = 2
            BitmapFactory.decodeStream(URL(url).openStream())

        } catch (e: Exception) {
            Log.e("error", "${e.message}")
            null
        }
    }
}
