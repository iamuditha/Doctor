package com.example.doctor

import android.content.Intent
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.doctor.introScreen.IntroActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.OnCompleteListener

open class BaseActivity : AppCompatActivity() {


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        Log.i("checklogout","i am called")

        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.i("checklogout","i am called")
        when (item.itemId) {

            R.id.logout -> {
                Log.i("checklogout","i am called 1 ")
                val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build()
                GoogleSignIn.getClient(this, gso).signOut()
                    .addOnCompleteListener(this, OnCompleteListener<Void?> {
                        val intent = Intent(applicationContext, IntroActivity::class.java)
                        startActivity(intent)
                        Toast.makeText(this, "Logout Successfully", Toast.LENGTH_SHORT).show()
                    }).addOnFailureListener {
                        Toast.makeText(this, "Issue with Logout", Toast.LENGTH_SHORT).show()
                    }
            }
        }
        return true
    }
}