package com.bumble.headline.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.bumble.headline.R
import com.bumble.headline.model.MessageEvent
import com.bumble.headline.repository.CountryRepository
import org.greenrobot.eventbus.EventBus

class SettingsViewModel : ViewModel() {


    fun getCountryList(context: Context?): ArrayList<String> {
        val countries = context!!.resources.getStringArray(R.array.country_arrays)
        var result:ArrayList<String> = ArrayList()

        countries.forEach {
            result.add(it)
        }
        return result
    }

    fun updateCountry(countrySpinnerSelected: String) {
        EventBus.getDefault().post(MessageEvent("Hey event subscriber!"));
        CountryRepository.updateSelecteCountryCode(countrySpinnerSelected)
    }

}
