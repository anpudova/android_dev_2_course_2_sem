package com.example.tasksproject.presentation.screen.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.tasksproject.R
import com.example.tasksproject.databinding.FragmentAddBinding
import com.example.tasksproject.presentation.adapter.CityViewPagerAdapter

class AddFragment: Fragment(R.layout.fragment_add) {

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!
    private var viewPagerAdapter: CityViewPagerAdapter? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentAddBinding.bind(view)

        //viewPagerAdapter = CityViewPagerAdapter()

        val preferences: SharedPreferences = requireActivity()
            .getSharedPreferences("preferences", Context.MODE_PRIVATE)
        val editPreferences: SharedPreferences.Editor = preferences.edit()

        with(binding) {
            btnAdd.setOnClickListener {
                val city: String = etCity.text.toString()
                if (city != "") {
                    editPreferences.putString(city, city)
                    editPreferences.apply()
                    var bundle = Bundle()
                    bundle.putString("new-city", city)
                    findNavController().navigate(
                        R.id.action_addFragment_to_mainFragment,
                        bundle
                    )
                }
            }
        }

    }

    fun addCity(city: String) {

    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
        println("TEST TAG - AddFragment onDestroy")
    }

    override fun onResume() {
        super.onResume()
        println("TEST TAG - AddFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        println("TEST TAG - AddFragment onPause")
    }
}