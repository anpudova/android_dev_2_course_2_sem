package com.example.tasksproject.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.tasksproject.R
import com.example.tasksproject.databinding.ActivityMainBinding
import com.example.tasksproject.db.DatabaseHandler
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding!!
    private lateinit var controller: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        applicationContext?.let {
            DatabaseHandler.dbInit(appContext = it)
        }
        controller = (supportFragmentManager.findFragmentById(R.id.container) as NavHostFragment).navController
        val bottomView = findViewById<BottomNavigationView>(R.id.btn_view)
        bottomView.setupWithNavController(controller)
    }
}