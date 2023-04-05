package com.example.rest_kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import okhttp3.*
import java.io.IOException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialize TextView
        val textView = findViewById<TextView>(R.id.textView1)
        val refreshButton = findViewById<Button>(R.id.button1)

        // Create OkHttp client
        val client = OkHttpClient()

        // Create request object
        val request = Request.Builder()
            .url("https://random-word-api.herokuapp.com/word")
            .build()

        // Make request asynchronously
        fun makeRequest() {
            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(call: Call, e: IOException) {
                    // Handle request failure
                    e.printStackTrace()
                }

                override fun onResponse(call: Call, response: Response) {
                    // Handle request success
                    val word = Gson().fromJson(response.body?.string(), Array<String>::class.java).firstOrNull()
                    runOnUiThread {
                        textView.text = word
                    }
                }
            })
        }

        //Make initial request
        makeRequest()

        //Set OnClickListener
        refreshButton.setOnClickListener {
            makeRequest()
        }
    }
}
