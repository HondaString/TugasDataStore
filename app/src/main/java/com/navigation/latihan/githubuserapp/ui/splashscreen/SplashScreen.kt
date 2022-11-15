package com.navigation.latihan.githubuserapp.ui.splashscreen

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.navigation.latihan.githubuserapp.DataStore.DarkNightModelFactory
import com.navigation.latihan.githubuserapp.DataStore.DarkNightView
import com.navigation.latihan.githubuserapp.DataStore.Darknight
import com.navigation.latihan.githubuserapp.databinding.ActivitySplashScreenBinding
import com.navigation.latihan.githubuserapp.ui.search.MainActivity

class SplashScreen : AppCompatActivity() {

    private lateinit var binding: ActivitySplashScreenBinding
    private val second = 3000L
    private val Context.DataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this@SplashScreen, MainActivity::class.java))
            finish()
        }, second)

        val pref = Darknight.getInstance(DataStore)
        val darkViewModel = ViewModelProvider(this, DarkNightModelFactory(pref)).get(
            DarkNightView::class.java
        )

        darkViewModel.getThemeSettings().observe(
            this
        ) { isDarkModeActive: Boolean ->
            if (isDarkModeActive) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                darkViewModel.saveThemeSetting(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                darkViewModel.saveThemeSetting(false)
            }
        }
    }
}

