package com.bumble.headline.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.bumble.headline.R
import com.bumble.headline.repository.CountryRepository

class SettingsViewModel : ViewModel() {

    fun getCountryList(context: Context?): ArrayList<String> {
        val countries = context!!.resources.getStringArray(R.array.country_arrays)
        val result: ArrayList<String> = ArrayList()

        countries.forEach {
            result.add(it)
        }
        return result
    }

    fun updateCountry(countrySpinnerSelected: String) {
        CountryRepository.updateSelectedCountryCode(countrySpinnerSelected)
    }

}
