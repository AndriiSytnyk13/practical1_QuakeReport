package com.example.android.quakereport.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.android.quakereport.databinding.ItemEarthquakeBinding
import com.example.android.quakereport.model.Earthquake
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class EarthquakeAdapter(context: Activity, earthquake: ArrayList<Earthquake>) :
    ArrayAdapter<Earthquake>(context, 0, earthquake) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemBinding = ItemEarthquakeBinding.inflate(inflater, parent, false)
        val earthquakeItem = getItem(position)
        itemBinding.apply {
            city.text = earthquakeItem?.city
            magnitude.text = earthquakeItem?.magnitude.toString()
            date.text = earthquakeItem?.let { getTimeFromMills(it.time, "LLL dd, yyyy") }
            time.text = earthquakeItem?.let { getTimeFromMills(it.time, "h:mm a") }
        }
        return itemBinding.root
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTimeFromMills(time: Long, dateFormat: String): String {
        val dateObject = Date(time)
        val dateFormatter = SimpleDateFormat(dateFormat)
        return dateFormatter.format(dateObject)
    }
}