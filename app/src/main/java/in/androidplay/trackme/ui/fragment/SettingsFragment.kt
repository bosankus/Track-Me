package `in`.androidplay.trackme.ui.fragment

import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.util.Constants.KEY_NAME
import `in`.androidplay.trackme.util.Constants.KEY_WEIGHT
import `in`.androidplay.trackme.util.Helper
import `in`.androidplay.trackme.util.Helper.showSnack
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_settings.*
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFromSharedPreference()
        setListeners()
    }

    private fun setListeners() {
        efabApplyChanges.setOnClickListener {
            val success = applyChangesToSharedPreference()
            if (success) {
                showSnack(
                    requireActivity().findViewById(R.id.fragment_settings),
                    "Details updated"
                )
            } else {
                showSnack(
                    requireActivity().findViewById(R.id.fragment_settings),
                    "Please enter all the fields"
                )
            }
        }
    }

    private fun loadFromSharedPreference() {
        etSetupName.setText(sharedPref.getString(KEY_NAME, ""))
        etSetupWeight.setText(sharedPref.getFloat(KEY_WEIGHT, 80f).toString())
    }

    private fun applyChangesToSharedPreference(): Boolean {
        val name = etSetupName.text.toString()
        val weight = etSetupWeight.text.toString()
        if (name.isEmpty() || weight.isEmpty()) {
            return false
        }
        sharedPref.edit()
            .putString(KEY_NAME, name)
            .putFloat(KEY_WEIGHT, weight.toFloat())
            .apply()
        return true
    }
}