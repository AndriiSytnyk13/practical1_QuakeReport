package com.example.android.quakereport.utils

import android.util.Log
import com.example.android.quakereport.model.Earthquake
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.nio.charset.Charset

class QueryUtils {

    companion object {
        const val REQUEST = "https://earthquake.usgs.gov/fdsnws/event/1/query?format=geojson&eventtype=earthquake&orderby=time&minmag=6&limit=10"

        private fun extractEarthquakes(json: String): ArrayList<Earthquake> {
            val earthquakes = ArrayList<Earthquake>()
            try {
                val root = JSONObject(json)
                val features: JSONArray = root.getJSONArray("features")
                for (i in 0 until features.length()) {
                    val currentEarthquake = features.getJSONObject(i)
                    val property = currentEarthquake.getJSONObject("properties")
                    val mag = property.getDouble("mag")
                    val place = property.getString("place")
                    val date = property.getLong("time")
                    val url = property.getString("url")
                    val earthquake = Earthquake(mag, place, date, url)
                    earthquakes.add(earthquake)
                }
            } catch (e: JSONException) {
                Log.e("QueryUtils", "Problem parsing the earthquake JSON results", e)
            }
            return earthquakes

        }

        fun fetchData(urlRequest: String): ArrayList<Earthquake> {
            val url = createUrl(urlRequest)
            var jsonResponse: String? = null
            try {
                    jsonResponse = makeHTTPRequest(url)
            } catch (e: IOException) {
                e.printStackTrace()
            }
            return extractEarthquakes(jsonResponse!!)
        }

        private fun createUrl(urlString: String): URL? {
            var url: URL? = null
            try {
                url = URL(urlString)
            } catch (e: MalformedURLException) {
                e.printStackTrace()
            }
            return url
        }

        private fun makeHTTPRequest(url:URL?): String {
            var jsonResponse = ""
            if(url == null) {
                return jsonResponse
            }
            var urlConnection: HttpURLConnection? = null
            var inputStream: InputStream? = null
            try {
                urlConnection = url.openConnection() as HttpURLConnection?
                urlConnection?.apply {
                    requestMethod = "GET"
                    connect()
                }
                if(urlConnection?.responseCode == 200) {
                    inputStream = urlConnection.inputStream
                    jsonResponse = readFromStream(inputStream)
                } else {
                    Log.e("TAG", "Error response code: " + urlConnection?.responseCode)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            } finally {
                urlConnection?.disconnect()
                inputStream?.close()
            }
            return jsonResponse
        }

        private fun readFromStream(inputStream: InputStream): String {
            val output = StringBuilder()
            val inputStreamReader = InputStreamReader(inputStream, Charset.forName("UTF-8"))
            val reader = BufferedReader(inputStreamReader)
            var line = reader.readLine()
            while (line != null) {
                output.append(line)
                line = reader.readLine()
            }
            return output.toString()
        }
    }

}