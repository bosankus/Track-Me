package `in`.androidplay.trackme.ui.fragment

import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.databinding.FragmentSetupBinding
import `in`.androidplay.trackme.util.Constants.KEY_FIRST_TIME_TOGGLE
import `in`.androidplay.trackme.util.Constants.KEY_NAME
import `in`.androidplay.trackme.util.Constants.KEY_WEIGHT
import `in`.androidplay.trackme.util.UIHelper.showSnack
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment() {

    private var _binding: FragmentSetupBinding? = null
    private val binding get() = _binding!!

    @Inject
    lateinit var sharedPreference: SharedPreferences

    @set:Inject
    var isFirstAppOpen = true

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isFirstAppOpen) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.fragment_setup, true)
                .build()
            findNavController().navigate(
                R.id.action_setupFragment_to_runFragment,
                savedInstanceState,
                navOptions
            )
        }

        setListeners()

    }

    private fun setListeners() {
        binding.fabNext.setOnClickListener {
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
        val name = binding.etSetupName.text.toString()
        val weight = binding.etSetupWeight.text.toString()
        if (name.isEmpty() || weight.isEmpty()) {
            return false
        }
        sharedPreference.edit()
            .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
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