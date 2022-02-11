package com.example.exerciseforcandidates

import android.app.Application
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import org.json.JSONObject
import java.net.URL

abstract class AbstractApplication : Application() {
    abstract val baseUrl: String
}

class Application : AbstractApplication() {
    override val baseUrl = "https://api.coincap.io"
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val vm =
            TimeViewModel((application as AbstractApplication).baseUrl)
        findViewById<Button>(R.id.button).setOnClickListener {
            vm.getBtcPrice()
        }
        vm.btcPrice.observe(this) {
            findViewById<TextView>(R.id.value).text = it
        }
        application
    }
}

class TimeViewModel(private val baseUrl: String) : ViewModel() {

    private val _btcPrice = MutableLiveData<String>()
    val btcPrice: LiveData<String> get() = _btcPrice

    fun getBtcPrice() {

        val handler = CoroutineExceptionHandler() { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }

        GlobalScope.launch(handler) {
            val btcPrice = JSONObject(URL("$baseUrl/v2/assets/bitcoin").readText())
                .getJSONObject("data")
                .getString("priceUsd")
            withContext(Dispatchers.Main) {
                _btcPrice.value = btcPrice
            }
        }
    }
}