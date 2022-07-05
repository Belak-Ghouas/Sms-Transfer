package com.belak.pipe.services

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder

class PipeService : Service() {
    // Binder given to clients
    private val binder = PipeBinder()

    override fun onBind(intent: Intent?): IBinder {
       return binder
    }



    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    inner class PipeBinder : Binder() {
        // Return this instance of LocalService so clients can call public methods
        fun getService(): PipeService = this@PipeService
    }
}