package com.candra.latihanmapbox

import android.app.Application
import com.mapbox.mapboxsdk.Mapbox

open class MyApplication : Application(){
    override fun onCreate() {
        super.onCreate()
        Mapbox.getInstance(applicationContext,getString(R.string.token_map_box))
    }
}