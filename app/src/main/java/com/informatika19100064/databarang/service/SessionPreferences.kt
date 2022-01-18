package com.informatika19100064.databarang.service

import android.app.Activity
import android.content.Context

class SessionPreferences(activity: Activity) {
    private val KEY_LOGIN = "LOGIN"
    private val KEY_USERNAME = "USERNAME"

    val sessionLogin = activity.getSharedPreferences(KEY_LOGIN, Context.MODE_PRIVATE)

    fun actionLogin(userName : String){
        val edit = sessionLogin.edit()
        edit.putString(KEY_USERNAME, userName)
        edit.apply()
    }
    fun actionLogout(){
        val edit = sessionLogin.edit()
        edit.clear().apply()
    }
    fun getUserName(): String? {
        return sessionLogin.getString(KEY_USERNAME, null)
    }
}