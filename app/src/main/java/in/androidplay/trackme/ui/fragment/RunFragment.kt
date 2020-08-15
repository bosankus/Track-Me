package `in`.androidplay.trackme.ui.fragment

import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.services.SortType
import `in`.androidplay.trackme.ui.adapter.RunAdapter
import `in`.androidplay.trackme.util.PermissionUtil.askPermissions
import `in`.androidplay.trackme.util.PermissionUtil.hasLocationPermission
import `in`.androidplay.trackme.viewmodel.MainViewModel
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_run.*
import pub.devrel.easypermissions.AppSettingsDialog
import pub.devrel.easypermissions.EasyPermissions
import javax.inject.Inject

@AndroidEntryPoint
class RunFragment : Fragment(R.layout.fragment_run), EasyPermissions.PermissionCallbacks {

    private val viewModel: MainViewModel by viewModels()

    private lateinit var runAdapter: RunAdapter

    @set:Inject
    var name = ""

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        tvHeading.text = "Hey $name"
        checkPermission()
        setRecyclerView()
        setListeners()
    }

    @SuppressLint("SetTextI18n")
    private fun setListeners() {
        fabOpenTrackingFragment.setOnClickListener {
            findNavController().navigate(R.id.action_runFragment_to_trackingFragment)
        }

        // TODO: Need to change this from spinner
        viewModel.sortType(SortType.CALORIES)
        viewModel.run.observe(requireActivity(), Observer {
            runAdapter.submitList(it)
        })
    }

    private fun setRecyclerView() = rvRun.apply {
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
}