package `in`.androidplay.trackme.ui.fragment

import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.databinding.FragmentSettingsBinding
import `in`.androidplay.trackme.util.Constants.KEY_NAME
import `in`.androidplay.trackme.util.Constants.KEY_WEIGHT
import `in`.androidplay.trackme.util.UIHelper.showSnack
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {

    private var _binding: FragmentSettingsBinding? = null
    private val binding get() = _binding!!


    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        loadFromSharedPreference()
        setListeners()
    }

    private fun setListeners() {
        binding.efabApplyChanges.setOnClickListener {
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
        binding.etSetupName.setText(sharedPref.getString(KEY_NAME, ""))
        binding.etSetupWeight.setText(sharedPref.getFloat(KEY_WEIGHT, 80f).toString())
    }

    private fun applyChangesToSharedPreference(): Boolean {
        val name = binding.etSetupName.text.toString()
        val weight = binding.etSetupWeight.text.toString()
        if (name.isEmpty() || weight.isEmpty()) {
            return false
        }
        sharedPref.edit()
            .putString(KEY_NAME, name)
            .putFloat(KEY_WEIGHT, weight.toFloat())
            .apply()
        return true
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}