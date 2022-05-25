package com.example.android.quakereport.view

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.android.quakereport.databinding.EarthquakeActivityBinding
import com.example.android.quakereport.utils.QueryUtils.Companion.extractEarthquakes

class EarthquakeActivity : AppCompatActivity() {

    companion object {
        val LOG_TAG: String = EarthquakeActivity::class.java.name
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val earthquakeActivityBinding = EarthquakeActivityBinding.inflate(layoutInflater)
        setContentView(earthquakeActivityBinding.root)
        val earthquakes = extractEarthquakes()
        val arrayAdapter = EarthquakeAdapter(this, earthquakes)
        earthquakeActivityBinding.list.adapter = arrayAdapter
    }
}
