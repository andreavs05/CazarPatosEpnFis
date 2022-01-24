package com.cachipuendoandrea.cazarpatos

import android.app.Activity
import android.content.Context

class SharedPreferencesManager(val actividad: Activity):FileHandler {
    override fun SaveInformation(datosAGrabar: Pair<String, String>) {
        var sharePref = actividad.getPreferences(Context.MODE_PRIVATE)
        var editor = sharePref.edit()
        editor.putString(LOGIN_KEY, datosAGrabar.first)
        editor.putString(PASSWORD_KEY, datosAGrabar.second)
        editor.apply()
    }

    override fun ReadInformation(): Pair<String, String> {
        var sharePref = actividad.getPreferences(Context.MODE_PRIVATE)
        val email = sharePref.getString(LOGIN_KEY,"").toString()
        val clave = sharePref.getString(PASSWORD_KEY,"").toString()
        return (email to clave)
    }
}