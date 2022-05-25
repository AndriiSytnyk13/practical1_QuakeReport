package com.example.android.quakereport.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import com.example.android.quakereport.databinding.EarthquakeActivityBinding
import com.example.android.quakereport.model.Earthquake
import com.example.android.quakereport.utils.QueryUtils.Companion.extractEarthquakes

class EarthquakeActivity : AppCompatActivity() {

    private lateinit var earthquakeActivityBinding: EarthquakeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        earthquakeActivityBinding = EarthquakeActivityBinding.inflate(layoutInflater)
        setContentView(earthquakeActivityBinding.root)
        val earthquakes = extractEarthquakes()
        val arrayAdapter = EarthquakeAdapter(this, earthquakes)
        earthquakeActivityBinding.list.adapter = arrayAdapter
        openItemUrl(arrayAdapter)
    }

    private fun openItemUrl(adapter: EarthquakeAdapter) {
        earthquakeActivityBinding.list.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val currentEarthquake = adapter.getItem(position)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(currentEarthquake?.url)))
            }
    }

}
