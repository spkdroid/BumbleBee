package com.bumble.headline.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.bumble.headline.R
import com.bumble.headline.repository.CountryRepository

class SettingsViewModel : ViewModel() {

    var countrySpinnerSelected:String = ""

    fun getCountryList(context: Context?): ArrayList<String> {
        val countries = context!!.resources.getStringArray(R.array.country_arrays)
        var result:ArrayList<String> = ArrayList()

        countries.forEach {
            result.add(it)
        }
        return result
    }

    fun updateCountry(countrySpinnerSelected: String) {
        CountryRepository.updateSelecteCountryCode(countrySpinnerSelected)
    }

}
