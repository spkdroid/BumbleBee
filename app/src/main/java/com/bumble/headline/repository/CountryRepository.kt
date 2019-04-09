package com.bumble.headline.repository

import android.content.Context
import android.content.SharedPreferences

object CountryRepository {

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var selectedCountry: String

    val countryCodeMap: HashMap<String, String> = hashMapOf("USA" to "us", "Canada" to "ca")


    fun initializeRepository(ctx: Context) {
        sharedPreferences = ctx!!.getSharedPreferences("CountryPref", Context.MODE_PRIVATE)
    }

    fun getSelectedCountryCode(): String {
        val countryKey = sharedPreferences.getString(selectedCountry, "USA")!!
        return countryCodeMap[countryKey].toString()
    }

    fun updateSelecteCountryCode(country:String):Boolean {
        val editor = sharedPreferences.edit()
        editor.putBoolean("flag", true)
        editor.putString("country", country)
        return editor.commit()
    }

}