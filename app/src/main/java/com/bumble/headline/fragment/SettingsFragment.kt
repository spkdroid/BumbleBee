package com.bumble.headline.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.bumble.headline.R
import com.bumble.headline.repository.CountryRepository
import com.bumble.headline.viewmodel.SettingsViewModel
import kotlinx.android.synthetic.main.settings_fragment.*


class SettingsFragment : Fragment() {

    companion object {
        fun newInstance() = SettingsFragment()
    }

    private lateinit var viewModel: SettingsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.settings_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(SettingsViewModel::class.java)
        // TODO: Use the ViewModel

        val categories = ArrayList<String>()
        categories.addAll(viewModel.getCountryList(context))

        val dataAdapter = ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, categories)
        countrySpinner.adapter = dataAdapter

        countryText.text = CountryRepository.getSelectedCountry()

        updateCountryButton.setOnClickListener {
            countryText.text = countrySpinner.selectedItem.toString()
            viewModel.updateCountry(countryText.text.toString())
        }
    }
}
