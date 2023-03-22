package com.example.tasksproject.presentation.screen.listener

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle


internal class WeatherLocationListener : LocationListener {

    var imHere: Location? = null

    override fun onLocationChanged(loc: Location) {
        imHere = loc
    }

    override fun onProviderDisabled(provider: String) {}
    override fun onProviderEnabled(provider: String) {}
    override fun onStatusChanged(provider: String, status: Int, extras: Bundle) {}

    @SuppressLint("MissingPermission")
    fun setUpLocationListener(context: Context)
    {
        val locationManager =
            context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

        val locationListener = WeatherLocationListener()

        locationManager.requestLocationUpdates(
            LocationManager.GPS_PROVIDER,
            5000,
            5f,
            locationListener
        )
        imHere = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
    }
}
