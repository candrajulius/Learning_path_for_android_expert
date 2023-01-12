package com.candra.latihan_lint

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MAINActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "Candra Julius Sinaga"
        supportActionBar?.subtitle = getString(R.string.app_name)
    }
}