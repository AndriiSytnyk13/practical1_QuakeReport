package com.example.android.quakereport.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.android.quakereport.databinding.EarthquakeActivityBinding
import com.example.android.quakereport.utils.QueryUtils.Companion.REQUEST
import com.example.android.quakereport.utils.QueryUtils.Companion.fetchData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


class EarthquakeActivity : AppCompatActivity() {

    private lateinit var earthquakeActivityBinding: EarthquakeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        earthquakeActivityBinding = EarthquakeActivityBinding.inflate(layoutInflater)
        setContentView(earthquakeActivityBinding.root)
        val list = CoroutineScope(Dispatchers.IO).async {
            fetchData(REQUEST)
        }
        lifecycleScope.launch {
            val arrayAdapter = EarthquakeAdapter(this@EarthquakeActivity, list.await())
            earthquakeActivityBinding.list.adapter = arrayAdapter
            openItemUrl(arrayAdapter)
        }
    }

    private fun openItemUrl(adapter: EarthquakeAdapter) {
        earthquakeActivityBinding.list.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                val currentEarthquake = adapter.getItem(position)
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(currentEarthquake?.url)))
            }
    }
}
