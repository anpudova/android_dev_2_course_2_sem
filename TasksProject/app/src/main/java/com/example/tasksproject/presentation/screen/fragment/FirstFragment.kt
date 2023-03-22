package com.example.tasksproject.presentation.screen.fragment

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
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.MutableCreationExtras
import com.example.tasksproject.R
import com.example.tasksproject.databinding.FragmentFirstBinding
import com.example.tasksproject.di.DataDependency
import com.example.tasksproject.di.ViewModelArgsKeys
import com.example.tasksproject.presentation.model.WeatherDataModel
import com.example.tasksproject.presentation.mvvm.FirstFragmentViewModel
import com.example.tasksproject.presentation.screen.listener.WeatherLocationListener
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class FirstFragment: Fragment(R.layout.fragment_first) {

    private var _binding: FragmentFirstBinding? = null
    private val binding get() = _binding!!
    private var lat: Float? = null
    private var lon: Float? = null
    private var locationListener: WeatherLocationListener? = null
    private var dataModel: WeatherDataModel? = null
    private var preferences: SharedPreferences? = null
    private var editPreferences: SharedPreferences.Editor? = null

    private val viewModel: FirstFragmentViewModel by viewModels(extrasProducer = {
        MutableCreationExtras().apply {
            set(ViewModelArgsKeys.getWeatherByCityNameUseCaseKey, DataDependency.getWeatherByCityNameUseCase)
            set(ViewModelArgsKeys.getWeatherByCoordsUseCaseKey, DataDependency.getWeatherByCoordsUseCase)
        }
    }) {
        FirstFragmentViewModel.factory
    }

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

        preferences = requireActivity()
            .getSharedPreferences("preferences", Context.MODE_PRIVATE)
        editPreferences = preferences?.edit()

        initViews()
        observeData()
    }

    private fun initViews() {
        with(binding) {
            btnReqByCity.setOnClickListener {
                val city = etCity.text.toString()
                if (city != "") {
                    viewModel.requestCityByName(city)
                } else {
                    Toast.makeText(
                        requireContext(), "Введите город.", Toast.LENGTH_SHORT
                    ).show()
                }
            }
            btnReqForCoordinates.setOnClickListener {
                permission.launch(android.Manifest.permission.ACCESS_FINE_LOCATION)
                if(lat == null || lon == null) {
                    Toast.makeText(requireContext(), "Не удалось определить местоположение.", Toast.LENGTH_SHORT).show()
                } else {
                    viewModel.requestCityByCoords(lat!!, lon!!)
                }
            }
            tvCity.setOnClickListener {
                if (dataModel != null) {
                    val city = dataModel?.city
                    if (city != "") {
                        editPreferences?.putString("arg-city", city)
                        editPreferences?.putString("arg-temp", dataModel?.temperature.toString())
                        editPreferences?.putString("arg-press", dataModel?.pressure.toString())
                        editPreferences?.putString("arg-hum", dataModel?.humidity.toString())
                        editPreferences?.putString("arg-speed", dataModel?.speed.toString())
                        editPreferences?.putString("arg-icon", dataModel?.icon)
                        editPreferences?.apply()
                        val fragment = SecondFragment.getInstance()
                        fragment.show(parentFragmentManager, SecondFragment.SECOND_FRAGMENT_TAG)
                    }
                }

            }
        }
    }

    private fun observeData() {
        with(binding) {
            viewModel.progressBarState.observe(viewLifecycleOwner) { isVisible ->
                progressBar.isVisible = isVisible
            }
            viewModel.weatherDataState.observe(viewLifecycleOwner) { weatherDataModel ->
                weatherDataModel?.let { data ->
                    tvCity.text = getString(R.string.city_pattern, data.city)
                    tvTemp.text = getString(R.string.temperature_pattern, data.temperature.toString())
                    dataModel = data
                }
            }
            viewModel.errorState.observe(viewLifecycleOwner) { ex ->
                ex?.let {
                    val errorMessage = (ex as? HttpException)?.message ?: ex.toString()
                    Toast.makeText(
                        requireContext(),
                        getString(R.string.exception_occurred_pattern, errorMessage),
                        Toast.LENGTH_SHORT
                    ).show()
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