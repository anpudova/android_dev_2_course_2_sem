package com.example.tasksproject.presentation.screen.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import com.bumptech.glide.Glide
import com.example.tasksproject.R
import com.example.tasksproject.databinding.FragmentSecondBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SecondFragment: BottomSheetDialogFragment(R.layout.fragment_second) {

    private var _binding: FragmentSecondBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSecondBinding.bind(view)

        val preferences: SharedPreferences = requireActivity()
            .getSharedPreferences("preferences", Context.MODE_PRIVATE)

        with(binding) {
            Glide.with(requireContext())
                .load("https://openweathermap.org/img/wn/" + preferences.getString("arg-icon", "02d") + ".png")
                .into(ivIcon)
            tvCity.text = getString(R.string.city_pattern, preferences.getString("arg-city", ""))
            tvTemp.text = getString(R.string.temperature_pattern, preferences.getString("arg-temp", ""))
            tvPress.text = getString(R.string.pressure_pattern, preferences.getString("arg-press", ""))
            tvHumidity.text = getString(R.string.humidity_pattern, preferences.getString("arg-hum", ""))
            tvWindSpeed.text = getString(R.string.wind_speed_pattern, preferences.getString("arg-speed", ""))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
        println("TEST TAG - SecondFragment onDestroy")
    }

    override fun onResume() {
        super.onResume()
        println("TEST TAG - SecondFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        println("TEST TAG - SecondFragment onPause")
    }

    companion object {
        const val SECOND_FRAGMENT_TAG = "SECOND_FRAGMENT_TAG"

        fun getInstance() = SecondFragment()
    }
}