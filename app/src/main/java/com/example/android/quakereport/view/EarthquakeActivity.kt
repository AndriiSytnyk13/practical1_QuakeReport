package com.example.android.quakereport.view

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.AdapterView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.android.quakereport.databinding.EarthquakeActivityBinding
import com.example.android.quakereport.model.Earthquake
import com.example.android.quakereport.utils.QueryUtils
import com.example.android.quakereport.utils.QueryUtils.Companion.REQUEST
import com.example.android.quakereport.utils.QueryUtils.Companion.fetchData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class EarthquakeActivity : AppCompatActivity() {

    private lateinit var earthquakeActivityBinding: EarthquakeActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        earthquakeActivityBinding = EarthquakeActivityBinding.inflate(layoutInflater)
        setContentView(earthquakeActivityBinding.root)
        val arrayList = ArrayList<Earthquake>()
        val arrayAdapter = EarthquakeAdapter(this, arrayList)
        earthquakeActivityBinding.list.adapter = arrayAdapter
        openItemUrl(arrayAdapter)
        CoroutineScope(Dispatchers.IO).launch {
            fetchData(REQUEST)
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
