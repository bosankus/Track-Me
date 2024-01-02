package `in`.androidplay.trackme.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.databinding.FragmentRunBinding
import `in`.androidplay.trackme.ui.adapter.RunAdapter
import `in`.androidplay.trackme.util.Constants.BACKGROUND_LOCATION
import `in`.androidplay.trackme.util.Constants.COARSE_LOCATION
import `in`.androidplay.trackme.util.Constants.FINE_LOCATION
import `in`.androidplay.trackme.util.Constants.PERMISSION_REQUEST_RATIONAL
import `in`.androidplay.trackme.util.Constants.POST_NOTIFICATION
import `in`.androidplay.trackme.util.PermissionUtil.askForPermissions
import `in`.androidplay.trackme.util.PermissionUtil.hasPermission
import `in`.androidplay.trackme.util.UIHelper.openPermissionSettingsPage
import `in`.androidplay.trackme.util.UIHelper.showAlert
import javax.inject.Inject


@AndroidEntryPoint
class RunFragment : Fragment(R.layout.fragment_run) {

    @set:Inject
    var name = ""

    // private val viewModel: MainViewModel by viewModels()
    private lateinit var runAdapter: RunAdapter
    private var _binding: FragmentRunBinding? = null
    private val binding get() = _binding!!

    // list of permissions
    private val permissionArray: Array<String> =
        arrayOf(
            POST_NOTIFICATION,
            FINE_LOCATION,
            COARSE_LOCATION,
            BACKGROUND_LOCATION,
        )

    // multi-permission launcher
    private val requestMultiplePermissionLauncher: ActivityResultLauncher<Array<String>> =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { result ->
            handlePermissionResult(result)
        }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentRunBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvHeading.text = "Hey $name"
        setRecyclerView()
        setListeners()
        requestMultiplePermissionLauncher.launch(permissionArray)
    }

    private fun setListeners() {
        binding.fabOpenTrackingFragment.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }

        // TODO: Need to change this from spinner
        /*viewModel.sortType(SortType.CALORIES)
        viewModel.run.observe(requireActivity()) { runList ->
            runList?.let {
                if (it.isNotEmpty()) {
                    runAdapter.submitList(it)
                    binding.tvEmptyRunList.visibility = View.INVISIBLE
                } else {
                    binding.tvEmptyRunList.visibility = View.VISIBLE
                }
            }
        }*/
    }

    private fun setRecyclerView() = binding.rvRun.apply {
        runAdapter = RunAdapter()
        adapter = runAdapter
    }

    private fun handlePermissionResult(result: Map<String, @JvmSuppressWildcards Boolean>) {
        val list = result.values.toMutableList()
        val permissionsList = mutableListOf<String>()
        var permissionsCount = 0

        for (i in list.indices) {
            if (shouldShowRequestPermissionRationale(permissionArray[i])) {
                permissionsList.add(permissionArray[i])
            } else if (!hasPermission(requireContext(), permissionArray[i])) {
                permissionsCount++
            }
        }

        if (permissionsList.isNotEmpty()) {
            // Some permissions are denied and can be asked again.
            askForPermissions(
                requireContext(),
                permissionArray,
                permissionsList,
                requestMultiplePermissionLauncher
            )
        } else

            if (permissionsCount > 0) {
                // Show alert dialog
                showAlert(
                    requireContext(),
                    PERMISSION_REQUEST_RATIONAL,
                ) { requireContext().openPermissionSettingsPage() }
            } else {
                // All permissions granted. Do your stuff
                Log.d("RunFragment", "All Permissions are now granted")
            }
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}