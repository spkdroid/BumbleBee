package com.bumble.headline

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import com.bumble.headline.repository.CountryRepository


class MainActivity : AppCompatActivity() {

    private lateinit var navHost: NavHostFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        CountryRepository.initializeRepository(applicationContext)

        navHost = supportFragmentManager
            .findFragmentById(R.id.navHost) as NavHostFragment
    }

    override fun onSupportNavigateUp(): Boolean {
        return navHost.navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_item, menu)
        return true
    }

}