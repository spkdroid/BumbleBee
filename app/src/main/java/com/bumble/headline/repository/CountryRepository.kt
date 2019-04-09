package com.bumble.headline.repository

import android.content.Context
import android.content.SharedPreferences

/**
 *    Country Repository
 *    Scope - Singleton
 *
 *    initializeRepository - initialize the shared preference
 *    getSelectedCountry -  Get the current selected country (Default Selection = "United States")
 *    getSelectedCountryCode - Get the Country code based on the selection
 *    updateSelectedCountryCode - Update the Country code on the sharedPreference
 *
 */

object CountryRepository {

    private var sharedPreferences: SharedPreferences? = null


    private val countryCodeMap: HashMap<String, String> = hashMapOf(
        "United States" to "us",
        "Canada" to "ca",
        "India" to "in",
        "Singapore" to "sg",
        "United Kingdom" to "uk",
        "France" to "fr",
        "Australia" to "au"
    )


    fun initializeRepository(ctx: Context) {
        sharedPreferences = ctx.getSharedPreferences("CountryPref", Context.MODE_PRIVATE)
    }

    fun getSelectedCountry(): String {
        return sharedPreferences!!.getString("country", "USA")
    }

    fun getSelectedCountryCode(): String {
        val countryKey = sharedPreferences!!.getString("country", "United States")!!
        return countryCodeMap[countryKey].toString()
    }

    fun updateSelectedCountryCode(country: String): Boolean {
        val editor = sharedPreferences!!.edit()
        editor.putBoolean("flag", true)
        editor.putString("country", country)
        return editor.commit()
    }

}
