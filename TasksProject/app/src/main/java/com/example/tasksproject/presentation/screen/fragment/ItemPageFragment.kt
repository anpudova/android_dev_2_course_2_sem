package com.example.tasksproject.presentation.screen.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.tasksproject.R
import com.example.tasksproject.databinding.ItemPageBinding
import com.example.tasksproject.db.model.CityModel

class ItemPageFragment(private val cityModel: CityModel?):
    Fragment(R.layout.item_page) {

    private var _binding: ItemPageBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = ItemPageBinding.bind(view)

        with(binding) {
            tvCity.text = cityModel?.name
            tvTemp.text = cityModel?.temp.toString()
            Glide.with(requireContext())
                .load("https://openweathermap.org/img/wn/" + cityModel?.icon + ".png")
                .into(ivIcon)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
        println("TEST TAG - ItemPageFragment onDestroy")
    }

    override fun onResume() {
        super.onResume()
        println("TEST TAG - ItemPageFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        println("TEST TAG - ItemPageFragment onPause")
    }

}