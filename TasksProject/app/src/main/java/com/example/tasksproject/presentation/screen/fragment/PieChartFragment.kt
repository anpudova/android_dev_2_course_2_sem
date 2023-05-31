package com.example.tasksproject.presentation.screen.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.tasksproject.R
import com.example.tasksproject.databinding.FragmentPieChartBinding
import com.example.tasksproject.presentation.screen.custom_view.PieChartView

class PieChartFragment: Fragment(R.layout.fragment_pie_chart) {

    private var _binding: FragmentPieChartBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentPieChartBinding.bind(view)

        val pieChartView = binding.pieChartView
        val data = listOf(
            Pair(Color.RED, 25f),
            Pair(Color.BLUE, 8f),
            Pair(Color.GRAY, 29f),
            Pair(Color.MAGENTA, 16f),
            Pair(Color.DKGRAY, 22f)
        )
        pieChartView.setData(data)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
        println("TEST TAG - PieChartFragment onDestroy")
    }

    override fun onResume() {
        super.onResume()
        println("TEST TAG - PieChartFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        println("TEST TAG - PieChartFragment onPause")
    }
}