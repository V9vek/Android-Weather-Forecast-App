package com.viveksharma.forecastmvvm.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.viveksharma.forecastmvvm.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        navController = this.findNavController(R.id.nav_host_fragment)

        bottom_nav.setupWithNavController(navController)

        NavigationUI.setupActionBarWithNavController(this, navController)

        window.navigationBarColor = resources.getColor(R.color.colorPrimary)
    }

    override fun onSupportNavigateUp(): Boolean {       //up button
        return NavigationUI.navigateUp(navController, null)
    }
}
