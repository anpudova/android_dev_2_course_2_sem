package com.example.tasksproject.presentation.screen.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.MutableCreationExtras
import androidx.navigation.fragment.findNavController
import com.example.tasksproject.R
import com.example.tasksproject.databinding.FragmentMainBinding
import com.example.tasksproject.db.DatabaseHandler
import com.example.tasksproject.db.model.CityModel
import com.example.tasksproject.db.model.CityUpdateModel
import com.example.tasksproject.di.DataDependency
import com.example.tasksproject.di.ViewModelArgsKeys
import com.example.tasksproject.presentation.adapter.CityViewPagerAdapter
import com.example.tasksproject.presentation.mvvm.MainFragmentViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlin.math.roundToInt

class MainFragment: Fragment(R.layout.fragment_main) {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!
    private var viewPagerAdapter: CityViewPagerAdapter? = null
    private var newCity: String? = null
    private var cities: List<CityModel?>? = null
    private val viewModel: MainFragmentViewModel by viewModels(extrasProducer = {
        MutableCreationExtras().apply {
            set(ViewModelArgsKeys.getWeatherByCityNameUseCaseKey, DataDependency.getWeatherByCityNameUseCase)
        }
    }) {
        MainFragmentViewModel.factory
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        initAdapter()

        newCity = arguments?.getString("new-city")

        if (newCity.isNullOrEmpty()) {
            lifecycleScope.launch {
                cities = DatabaseHandler.getCities()
                if (cities.isNullOrEmpty()) {
                    findNavController().navigate(
                        R.id.action_mainFragment_to_addFragment
                    )
                } else {
                    initViews()
                    for (i in cities!!.indices) {
                        observeData(cities!![i]?.name)
                    }
                    viewPagerAdapter?.updateCities(cities!!)
                }
            }
        } else {
            initViews()
            observeData(newCity)
        }
    }

    private fun initViews() {
        with(binding) {
            btnAdd.setOnClickListener {
                findNavController().navigate(
                    R.id.action_mainFragment_to_addFragment
                )
            }
        }
    }

    private fun observeData(city: String?) {
        viewModel.requestCityByName(city.toString())
        viewModel.weatherDataState.observe(viewLifecycleOwner) { weatherDataModel ->
            weatherDataModel?.let { data ->
                lifecycleScope.launch {
                    val cityModelGet = DatabaseHandler.getCity(data.city)
                    if (cityModelGet != null) {
                        val cityUpdateModel = CityUpdateModel (
                                cityModelGet.id,
                                data.temperature,
                                data.icon
                            )
                        DatabaseHandler.updateCity(cityUpdateModel)
                    } else {
                        val id: Int = (Math.random() * 10000000 + 1).roundToInt()
                        val cityModel = CityModel(
                            id,
                            data.city,
                            data.temperature,
                            data.icon
                        )
                        DatabaseHandler.createCity(cityModel)
                    }
                    cities = DatabaseHandler.getCities()
                    viewPagerAdapter?.updateCities(cities)
                }
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

    private fun initAdapter() {
        viewPagerAdapter = CityViewPagerAdapter(childFragmentManager, viewLifecycleOwner.lifecycle)
        with(binding) {
            viewPager.adapter = viewPagerAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
        println("TEST TAG - MainFragment onDestroy")
    }

    override fun onResume() {
        super.onResume()
        println("TEST TAG - MainFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        println("TEST TAG - MainFragment onPause")
    }
}