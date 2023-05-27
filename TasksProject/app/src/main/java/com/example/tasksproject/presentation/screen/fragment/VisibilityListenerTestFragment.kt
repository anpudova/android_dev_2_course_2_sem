package com.example.tasksproject.presentation.screen.fragment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.tasksproject.R
import com.example.tasksproject.databinding.FragmentVisibilityListenerTestBinding
import com.example.tasksproject.presentation.adapter.VisibilityListenerTestAdapter
import com.example.tasksproject.presentation.screen.listener.VisibilityListener

class VisibilityListenerTestFragment: Fragment(R.layout.fragment_visibility_listener_test) {

    private var rvListenerTestAdapter: VisibilityListenerTestAdapter? = null
    private var _binding: FragmentVisibilityListenerTestBinding? = null
    private val binding get() = _binding!!

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentVisibilityListenerTestBinding.bind(view)
        initAdapter()
    }
    private fun initAdapter() {
        rvListenerTestAdapter = VisibilityListenerTestAdapter().apply {
            items = initList()
        }

        with(binding) {
            recyclerView.adapter = rvListenerTestAdapter
            recyclerView.layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            val listener = VisibilityListener(50)
            recyclerView.addOnScrollListener(listener)
        }
    }

    private fun initList(): ArrayList<String> {
        var list: ArrayList<String> = arrayListOf()
        for (i in 0..20) {
            list.add("Element ${i+1}")
        }
        return list
    }

    override fun onDestroyView() {
        super.onDestroyView()
        this._binding = null
        println("TEST TAG - VisibilityListenerTestFragment onDestroy")
    }

    override fun onResume() {
        super.onResume()
        println("TEST TAG - VisibilityListenerTestFragment onResume")
    }

    override fun onPause() {
        super.onPause()
        println("TEST TAG - VisibilityListenerTestFragment onPause")
    }
}