package com.wattpad.mystory.repository

object CountryRepository {

    private lateinit var selectedCountry: String

    fun getSelectedCountry() : String
    {
        if(selectedCountry == null) {
            return "us"
        }
        return "us"
    }

    fun setSelectedCountry(countryCode:String) {
        selectedCountry = countryCode
    }
}