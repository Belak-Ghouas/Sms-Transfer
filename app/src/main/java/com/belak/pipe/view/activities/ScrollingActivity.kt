package com.belak.pipe.view.activities

import android.Manifest
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.belak.pipe.R
import com.belak.pipe.databinding.ActivityScrollingBinding
import com.belak.pipe.services.PipeService
import com.belak.pipe.view.viewmodels.BaseActivityViewModel
import com.google.android.material.snackbar.Snackbar
import org.koin.core.module.Module

class ScrollingActivity : BaseActivity<BaseActivityViewModel,ActivityScrollingBinding>() {


    override val moduleList: List<Module> = emptyList()
    private lateinit var mService: PipeService
    private var mBound: Boolean = false

    /** Defines callbacks for service binding, passed to bindService()  */
    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder = service as PipeService.PipeBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setSupportActionBar(findViewById(R.id.toolbar))
        binding.toolbarLayout.title = title
        binding.fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
        askPermission()
        Log.e("MainActivity","onCreate")
    }


    override fun getViewBinding() = ActivityScrollingBinding.inflate(layoutInflater)

    override fun initViews() {
    }

    override fun initObservers() {
    }



    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_scrolling, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

   private fun askPermission(){

       val permissions = arrayOf(Manifest.permission.RECEIVE_SMS,Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_MMS,Manifest.permission_group.SMS)
       val listToAsk = arrayListOf<String>()
       for (permission in permissions){
           if(ContextCompat.checkSelfPermission(this,permission) != PackageManager.PERMISSION_GRANTED){
               listToAsk.add(permission)
           }
       }
       if(listToAsk.isNotEmpty()){
           ActivityCompat.requestPermissions(this, listToAsk.toTypedArray(), 1)
       }

   }



    override fun onStart() {
        super.onStart()
        // Bind to LocalService
        Intent(this, PipeService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
    }

}