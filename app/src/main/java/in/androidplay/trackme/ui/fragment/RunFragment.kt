package `in`.androidplay.trackme.ui.fragment

import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.viewmodel.MainViewModel
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_run.*

@AndroidEntryPoint
class RunFragment : Fragment(R.layout.fragment_run) {

    private val viewModel: MainViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvBack.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }
    }
}