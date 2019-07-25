package com.gfakuntansi.gf_pricelist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity

private const val AUTH_ACTIVITY = 1000
abstract class SplashedActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

        if (!isAuthenticated()) {
            startActivityForResult(Intent(this, AuthActivity::class.java), AUTH_ACTIVITY)
        }

        super.onCreate(savedInstanceState)
    }

    private fun isAuthenticated() : Boolean {
        return getUserDetails() != null
    }

    private fun onAuthenticatedCallBack(resultCode : Int, data : Intent?) {
        when(resultCode) {
            Activity.RESULT_CANCELED -> finish()
            Activity.RESULT_OK -> recreate()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            AUTH_ACTIVITY -> onAuthenticatedCallBack(resultCode, data)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}

