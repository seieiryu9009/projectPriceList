package com.gfakuntansi.gf_pricelist

import android.content.Context
import android.content.Intent
import android.preference.PreferenceManager

data class UserStorage(val username : String?, val password : String?)

private const val KEY_USER = "user"
private const val KEY_PASS = "password"

fun Context.getUserDetails() : UserStorage? =
    PreferenceManager
        .getDefaultSharedPreferences(this)
        .let { sharedPreferences ->
            val username = sharedPreferences.getString(KEY_USER, null)
            val password = sharedPreferences.getString(KEY_PASS, null)
            if (username?.isNotBlank() == true && password?.isNotBlank() == true) {
                UserStorage(username, password)
            } else {
                null
            }
        }


fun Context.saveUser( user : UserStorage) =
        PreferenceManager
            .getDefaultSharedPreferences(this)
            .edit()
            .also { sharedPreferences ->
                sharedPreferences.putString(KEY_USER, user.username)
                sharedPreferences.putString(KEY_PASS, user.password)
            }
            .apply()

fun Context.logoutUser() =
        PreferenceManager
            .getDefaultSharedPreferences(this)
            .edit()
            .clear()
            .apply()