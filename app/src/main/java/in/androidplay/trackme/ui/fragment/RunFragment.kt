package `in`.androidplay.trackme.ui.fragment

import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.data.viewmodel.MainViewModel
import `in`.androidplay.trackme.databinding.FragmentRunBinding
import `in`.androidplay.trackme.services.SortType
import `in`.androidplay.trackme.ui.adapter.RunAdapter
import `in`.androidplay.trackme.util.PermissionUtil.askPermissions
import `in`.androidplay.trackme.util.PermissionUtil.hasLocationPermission
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

@AndroidEntryPoint
class RunFragment : Fragment(R.layout.fragment_run), EasyPermissions.PermissionCallbacks {

    private var _binding: FragmentRunBinding? = null
    private val binding get() = _binding!!

    private val viewModel: MainViewModel by viewModels()

    private lateinit var runAdapter: RunAdapter

    @set:Inject
    var name = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        _binding = FragmentRunBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.tvHeading.text = "Hey $name"
        checkPermission()
        setRecyclerView()
        setListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun setListeners() {
        binding.fabOpenTrackingFragment.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }

        // TODO: Need to change this from spinner
        viewModel.sortType(SortType.CALORIES)
        viewModel.run.observe(requireActivity()) { runAdapter.submitList(it) }
    }

    private fun setRecyclerView() = binding.rvRun.apply {
        runAdapter = RunAdapter()
        adapter = runAdapter
    }

    private fun checkPermission() {
        if (!hasLocationPermission(requireContext())) {
            askPermissions(this)
        }
    }

    override fun onPermissionsDenied(requestCode: Int, perms: MutableList<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            AppSettingsDialog.Builder(this).build()
        } else askPermissions(this)
    }

    override fun onPermissionsGranted(requestCode: Int, perms: MutableList<String>) {}

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}