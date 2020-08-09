package `in`.androidplay.trackme.ui.fragment

import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.util.Constants.KEY_FIRST_TIME_TOGGLE
import `in`.androidplay.trackme.util.Constants.KEY_NAME
import `in`.androidplay.trackme.util.Constants.KEY_WEIGHT
import `in`.androidplay.trackme.util.Helper.showSnack
import android.content.SharedPreferences
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_setup.*
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment(R.layout.fragment_setup) {

    @Inject
    private lateinit var sharedPreference: SharedPreferences

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setListeners()

    }

    private fun setListeners() {
        fabNext.setOnClickListener {
            val success = writeDataToSharedPreference()
            if (success) {
                findNavController().navigate(R.id.action_setupFragment_to_runFragment)
            } else {
                showSnack(
                    requireActivity().findViewById(R.id.fragment_setup),
                    "Please enter all the fields"
                )
            }
        }
    }

    private fun writeDataToSharedPreference(): Boolean {
        val name = etSetupName.text.toString()
        val weight = etSetupWeight.text.toString()
        if (name.isEmpty() || name.isEmpty()) return false
        sharedPreference.edit()
            .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
            .putString(KEY_NAME, name)
            .putString(KEY_WEIGHT, weight)
            .apply()
        return true
    }

}