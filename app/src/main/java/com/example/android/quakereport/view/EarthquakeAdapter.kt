package com.example.android.quakereport.view

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.core.content.ContextCompat
import com.example.android.quakereport.R
import com.example.android.quakereport.databinding.ItemEarthquakeBinding
import com.example.android.quakereport.model.Earthquake
import java.text.SimpleDateFormat
import java.util.Date

class EarthquakeAdapter(context: Activity, earthquake: ArrayList<Earthquake>) :
    ArrayAdapter<Earthquake>(context, 0, earthquake) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {

        val inflater =
            context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val itemBinding = ItemEarthquakeBinding.inflate(inflater, parent, false)
        val earthquakeItem = getItem(position)
        itemBinding.apply {
            magnitude.text = earthquakeItem!!.magnitude.toString()
            val magCircle:GradientDrawable = magnitude.background as GradientDrawable
            val magColor = getMagColor(earthquakeItem.magnitude)
            magCircle.setColor(magColor)
            city.text = earthquakeItem.city
            date.text = getTimeFromMills(earthquakeItem.time, "LLL dd, yyyy")
            time.text = getTimeFromMills(earthquakeItem.time, "h:mm a")
        }
        return itemBinding.root
    }

    @SuppressLint("SimpleDateFormat")
    private fun getTimeFromMills(time: Long, dateFormat: String): String {
        val dateObject = Date(time)
        val dateFormatter = SimpleDateFormat(dateFormat)
        return dateFormatter.format(dateObject)
    }

    private fun getMagColor(mag: Double): Int{
        var color = 0
        when(mag.toInt()) {
            1 -> color = R.color.magnitude1
            2 -> color = R.color.magnitude2
            3 -> color = R.color.magnitude3
            4 -> color = R.color.magnitude4
            5 -> color = R.color.magnitude5
            6 -> color = R.color.magnitude6
            7 -> color = R.color.magnitude7
            8 -> color = R.color.magnitude8
            9 -> color = R.color.magnitude9
            10 -> color = R.color.magnitude10plus
        }
        return ContextCompat.getColor(context, color)
    }
}