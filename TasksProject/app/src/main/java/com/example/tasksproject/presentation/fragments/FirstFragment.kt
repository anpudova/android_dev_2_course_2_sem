package com.example.tasksproject.presentation.fragments

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.tasksproject.R
import com.example.tasksproject.data.model.response.WeatherResponse
import com.example.tasksproject.data.repository.WeatherRepository
import com.example.tasksproject.databinding.FragmentFirstBinding
import com.example.tasksproject.presentation.listeners.WeatherLocationListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class FirstFragment: Fragment(R.layout.fragment_first) {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private var lat: Float? = null
    private var lon: Float? = null
    private var locationListener: WeatherLocationListener? = null
    private var response: WeatherResponse? = null
    private val permission = registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted ->
        if (isGranted) {
            locationListener = WeatherLocationListener()
            if (locationListener != null) {
                locationListener!!.setUpLocationListener(requireContext())
                lat = locationListener!!.imHere?.latitude?.toFloat()
                lon = locationListener!!.imHere?.longitude?.toFloat()
            }
        } else {
            if (ActivityCompat.shouldShowRequestPermissionRationale(requireActivity(), android.Manifest.permission.ACCESS_FINE_LOCATION)) {
                AlertDialog.Builder(requireContext())
                    .setTitle("Требуется разрешение")
                    .setMessage("Если вы хотите использовать геолокацию в этом приложении, дайте разрешение")
                    .show()
            } else {
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.fromParts("package", requireActivity().packageName, null)
                )
                startActivity(intent)
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentFirstBinding.bind(view)
        val repo = WeatherRepository()

        val preferences: SharedPreferences = requireActivity()
            .getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val editPreferences: SharedPreferences.Editor = preferences.edit()

        with(binding) {
            btnReqByCity.setOnClickListener {
                val city = etCity.text.toString()
                if (city != "") {
                    progressBar.isVisible = true
                    lifecycleScope.launch {
                        runCatching {
                            repo.getWeatherInfoByCityName(city = city)
                        }.onSuccess {
                            progressBar.isVisible = false
                            tvCity.text = it.city.toString();
                            tvTemp.text = it.main?.temp.toString()
                            response = it
                        }.onFailure {
                            progressBar.isVisible = false
                            Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(context, "Введите город.", Toast.LENGTH_SHORT).show()
                }
            }
            btnReqForCoordinates.setOnClickListener {
                lifecycleScope.launch {
                    runCatching {
                        permission.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                        progressBar.isVisible = true
                        while(lat == null || lon == null) {
                            delay(500)
                        }
                        repo.getWeatherInfoByCoords(lat!!, lon!!)
                    }.onSuccess {
                        progressBar.isVisible = false
                        tvCity.text = it.city.toString();
                        tvTemp.text = it.main?.temp.toString()
                        response = it
                    }.onFailure {
                        progressBar.isVisible = false
                        Toast.makeText(context, "Error: $it", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            tvCity.setOnClickListener {
                val city = response?.city.toString()
                if (city != "") {
                    editPreferences.putString("arg-city", city)
                    editPreferences.putString("arg-temp", response?.main?.temp.toString())
                    editPreferences.putString("arg-press", response?.main?.pressure.toString())
                    editPreferences.putString("arg-hum", response?.main?.humidity.toString())
                    editPreferences.putString("arg-speed", response?.wind?.speed.toString())
                    editPreferences.putString("arg-icon", response?.weatherList?.get(0)?.icon)
                    editPreferences.apply()
                    val fragment = SecondFragment.getInstance()
                    fragment.show(parentFragmentManager, SecondFragment.SECOND_FRAGMENT_TAG)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
        println("TEST TAG - FirstFragment onDestroy")
    }

    override fun onResume() {
        super.onResume()
        println("TEST TAG - FirstFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        println("TEST TAG - FirstFragment onPause")
    }
}