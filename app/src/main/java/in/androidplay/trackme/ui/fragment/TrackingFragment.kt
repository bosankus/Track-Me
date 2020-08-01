package `in`.androidplay.trackme.ui.fragment

import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.services.TrackingService
import `in`.androidplay.trackme.util.Constants.ACTION_START_OR_RESUME_SERVICE
import `in`.androidplay.trackme.viewmodel.MainViewModel
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.OnMapReadyCallback
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.MapStyleOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tracking.*

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking), OnMapReadyCallback {

    private val viewModel: MainViewModel by viewModels()

    private var map: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)

        mapView.getMapAsync(this)

        setListeners()
    }

    private fun setListeners() {
        efabStartTimer.setOnClickListener { sendCommandToService(ACTION_START_OR_RESUME_SERVICE) }
    }

    private fun sendCommandToService(action: String) {
        Intent(requireContext(), TrackingService::class.java).also {
            it.action = action
            requireContext().startService(it)
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        this.map = googleMap
        val style = MapStyleOptions.loadRawResourceStyle(requireContext(), R.raw.style_json)
        map?.setMapStyle(style)
        map?.moveCamera(CameraUpdateFactory.newLatLng(LatLng(-34.00, 151.00)))
    }
}