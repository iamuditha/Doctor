package com.example.doctor.qr

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import androidx.drawerlayout.widget.DrawerLayout
import com.example.doctor.BaseActivity
import com.example.doctor.R
import com.example.doctor.recordList.RecordListActivity
import com.example.doctor.utils.NoInternetDialogFragment
import kotlinx.android.synthetic.main.activity_q_r.*
import kotlinx.android.synthetic.main.toolbar.*


class QRActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_q_r)

        setSupportActionBar(toolbar_main)


        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)


//        val actionBarDrawerToggle = ActionBarDrawerToggle(this,drawerLayout,toolbar_main,R.string.open,R.string.close)
//        drawerLayout.addDrawerListener(actionBarDrawerToggle)
//        actionBarDrawerToggle.syncState()
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        val did = intent.getStringExtra("did")

        val qrHolder = findViewById<ImageView>(R.id.qrholder)
        QRGenerator().generateQRCode(did, qrHolder)

        //view button click event
        view.setOnClickListener {
            qrAnimation.visibility = View.GONE
            qrHolder.visibility = View.VISIBLE
            view.visibility = View.GONE
            hide.visibility = View.VISIBLE
        }


        //hide button click event
        hide.setOnClickListener {
            qrHolder.visibility = View.GONE
            qrAnimation.visibility = View.VISIBLE
            hide.visibility = View.GONE

            view.visibility = View.VISIBLE
        }

        //connect button click event
        connectButton.setOnClickListener {
            val intent = Intent(this, RecordListActivity::class.java)
            startActivity(intent)
//            connectingState()
//            if (!isNetworkAvailable()) {
//                displayAlert()
//                connectState()
//            }
//            /*******only for testing ***********/
//            Handler(Looper.getMainLooper()).postDelayed({
//                val intent = Intent(this, RecordListActivity::class.java)
//                startActivity(intent)
//            }, 200)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //change ui for the connecting state
    private fun connectingState() {
        connectButton.visibility = View.GONE
        connecting.visibility = View.VISIBLE
    }

    //change ui for the connect state
    private fun connectState() {
        connecting.visibility = View.GONE
        connectButton.visibility = View.VISIBLE
    }

    //check if the internet is available
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    //display the no internet alert
    private fun displayAlert() {
        val newFragment = NoInternetDialogFragment()
        newFragment.show(supportFragmentManager, "noInternet")
    }

}
