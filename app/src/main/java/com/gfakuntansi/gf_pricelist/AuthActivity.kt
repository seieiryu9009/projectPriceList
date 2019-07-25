package com.gfakuntansi.gf_pricelist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.android.volley.AuthFailureError
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.auth_activity.*
import java.lang.Exception

class AuthActivity : AppCompatActivity() {

    private val btnLogin by lazy { findViewById<Button>(R.id.auth_button) }
    private val btnCancel by lazy { findViewById<Button>(R.id.cancel_button) }
    private val userEditText by lazy { findViewById<EditText>(R.id.user_ed) }
    private val passEditText by lazy { findViewById<EditText>(R.id.pass_ed) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.auth_activity)

        val header = header
        val body = body

        btnLogin.setOnClickListener {
            if (validateInput()) {
                loginAuth()
            }
        }

        btnCancel.setOnClickListener {
            finish()
        }
    }

    private fun validateInput() : Boolean {
        if (userEditText.text.isBlank()){
            userEditText.error = getString(R.string.empty_username)
            userEditText.requestFocus()
            return false
        }
        if (passEditText.text.isBlank()) {
            passEditText.error = getString(R.string.empty_password)
            passEditText.requestFocus()
            return false
        }
        return true
    }


    private fun loginAuth() {
        val username = userEditText.text.toString().trim()
        val password = passEditText.text.toString().trim()

        val stringRequest = object : StringRequest(Method.POST, AppVar.LOGIN_URL,
            Listener { response ->
                try {
                    if (response.contains(AppVar.SUCCESS_ECHO)) {
                        saveUser(UserStorage(
                            username,
                            password
                        ))
                        loadDashboard()
                        Toast.makeText(applicationContext, "Successful Login", Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, "Incorrect Username or Password", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            },
            Response.ErrorListener {
                error ->
                    Toast.makeText(applicationContext, error.toString(), Toast.LENGTH_SHORT).show()
            }) {
            @Throws(AuthFailureError::class)
            override fun getParams() : Map<String, String> {
                val params = HashMap<String, String>()
                //Adding param to request
                params[AppVar.KEY_USER] = username
                params[AppVar.KEY_PASSWORD] = password

                //returning params
                return params
            }
        }
        Volley.newRequestQueue(this).add(stringRequest)
    }

    private fun loadDashboard() {
        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }

}

