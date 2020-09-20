package com.example.doctor.introScreen

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import com.example.doctor.DoctorRegisterActivity
import com.example.doctor.qr.QRActivity
import com.example.doctor.R
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.android.material.tabs.TabLayout
import kotlinx.android.synthetic.main.activity_intro.*

class IntroActivity : AppCompatActivity() {

    private val introScreenRepository =
        IntroScreenRepository()
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private val RC_SIGN_IN = 100;

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_intro)

        // get intro screen tab layout data
        val mList = introScreenRepository.fetchTabLayoutData()

        val screenPager = findViewById<ViewPager>(R.id.screen_viewpager)
        val tabIndicator = findViewById<TabLayout>(R.id.tab_indicator)

        // setup viewpager
        val introViewPagerAdapter =
            IntroViewPageAdapter(this, mList)
        screenPager.adapter = introViewPagerAdapter

        // setup tab layout with viewpager
        tabIndicator.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        tabIndicator.setupWithViewPager(screenPager)

        // Configure sign-in to request the user's ID, email address, and basic profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        sign_in_button.setOnClickListener { v ->
            when (v.id) {
                R.id.sign_in_button -> signIn()
            }
        }
    }

    private fun signIn() {
        val signInIntent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onStart() {
        super.onStart()
        // Check for existing Google Sign In account, if the user is already signed in the GoogleSignInAccount will be non-null.
        val account = GoogleSignIn.getLastSignedInAccount(this)
        account?.let { getProfileData(it) }
        if (account != null) {
            val intent = Intent(this, DoctorRegisterActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            account?.let { getProfileData(it) }
            // Signed in successfully, show authenticated UI.
            val intent = Intent(this, DoctorRegisterActivity::class.java)
            startActivity(intent)
        } catch (e: ApiException) {
            Log.w("SignedIn", "signInResult:failed code=" + e.statusCode)
            //updateUI(null)
        }
    }

    private fun getProfileData (account: GoogleSignInAccount) {
        Log.i("SignedIn", account.displayName)
        Log.i("SignedIn", account.email)
    }

}
