package com.example.quizzy

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navController: NavController



    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        // Zugriff auf den NavHostFragment
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fragmentContainerView) as NavHostFragment



//region NavigationDrawer
        navController = navHostFragment.navController

        // DrawerLayout und NavigationView referenzieren (IDs aus deinem Layout anpassen)
        drawerLayout = findViewById(R.id.drawerLayout)
        val navigationView = findViewById<NavigationView>(R.id.navigationView)

        // Menü Item Klicks abfangen und navigieren
        navigationView.setNavigationItemSelectedListener { menuItem ->
            // Navigiere zu Fragment mit Menü-ID (entspricht fragment ID im NavGraph)
            navController.navigate(menuItem.itemId)

            // Drawer schließen
            drawerLayout.closeDrawer(GravityCompat.END)
            true
        }

//endregion


    }
}