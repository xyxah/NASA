package com.gb.nasa.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.gb.nasa.ui.pod.PictureOfTheDayFragment
import com.gb.nasa.R
import com.gb.nasa.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, PictureOfTheDayFragment.newInstance())
                .commitNow()
        }
    }
}