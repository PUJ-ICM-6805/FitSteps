package com.example.fitsteps.firebaseData.firebaseOwnProgramData.rest

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import com.android.volley.RequestQueue
import com.android.volley.toolbox.Volley

class VolleySingleton(context: Context) {
    private var mRequestQueue: RequestQueue? = null
    private var mInstance: VolleySingleton? = null

    init {
        mRequestQueue = Volley.newRequestQueue(context)
    }

    fun getInstance(context: Context): VolleySingleton? {
        if (mInstance == null) {
            mInstance = VolleySingleton(context)
        }
        return mInstance
    }

    fun getRequestQueue(): RequestQueue? {
        return mRequestQueue
    }


}