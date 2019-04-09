package com.bumble.headline.repository

import android.content.Context
import android.content.SharedPreferences

object CountryRepository {

    private var sharedPreferences: SharedPreferences? = null


    private val countryCodeMap: HashMap<String, String> = hashMapOf("USA" to "us", "Canada" to "ca","India" to "in")


    fun initializeRepository(ctx: Context) {
        sharedPreferences = ctx!!.getSharedPreferences("CountryPref", Context.MODE_PRIVATE)
    }


    fun getSelectedCountry():String {
        return sharedPreferences!!.getString("country","USA")
    }

    fun getSelectedCountryCode(): String {
        val countryKey = sharedPreferences!!.getString("country", "USA")!!
        return countryCodeMap[countryKey].toString()
    }

    fun updateSelecteCountryCode(country:String):Boolean {
        val editor = sharedPreferences!!.edit()
        editor.putBoolean("flag", true)
        editor.putString("country", country)
        return editor.commit()
    }

}
