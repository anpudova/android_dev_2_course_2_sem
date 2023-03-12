package com.example.tasksproject.presentation.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.tasksproject.R
import com.example.tasksproject.data.repository.WeatherRepository
import com.example.tasksproject.databinding.FragmentSecondBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.coroutines.launch

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
            tvCity.text = preferences.getString("arg-city", "city")
            tvTemp.text = preferences.getString("arg-temp", "0.0")
            tvPress.text = preferences.getString("arg-press", "0.0")
            tvHumidity.text = preferences.getString("arg-hum", "0.0")
            tvWindSpeed.text = preferences.getString("arg-speed", "0.0")
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