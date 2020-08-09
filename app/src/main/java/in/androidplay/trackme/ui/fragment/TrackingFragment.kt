package `in`.androidplay.trackme.ui.fragment

import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.room.Run
import `in`.androidplay.trackme.services.PolyLine
import `in`.androidplay.trackme.services.TrackingService
import `in`.androidplay.trackme.util.Constants.ACTION_PAUSE_SERVICE
import `in`.androidplay.trackme.util.Constants.ACTION_START_OR_RESUME_SERVICE
import `in`.androidplay.trackme.util.Constants.ACTION_STOP_SERVICE
import `in`.androidplay.trackme.util.Constants.MAP_CAMERA_ZOOM
import `in`.androidplay.trackme.util.Constants.POLYLINE_COLOR
import `in`.androidplay.trackme.util.Constants.POLYLINE_WIDTH
import `in`.androidplay.trackme.util.Helper.showSnack
import `in`.androidplay.trackme.util.TimeFormatUtil.calculatePolylineLength
import `in`.androidplay.trackme.util.TimeFormatUtil.getFormattedStopwatchTime
import `in`.androidplay.trackme.viewmodel.MainViewModel
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.google.android.libraries.maps.CameraUpdateFactory
import com.google.android.libraries.maps.GoogleMap
import com.google.android.libraries.maps.OnMapReadyCallback
import com.google.android.libraries.maps.model.LatLng
import com.google.android.libraries.maps.model.LatLngBounds
import com.google.android.libraries.maps.model.MapStyleOptions.loadRawResourceStyle
import com.google.android.libraries.maps.model.PolylineOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tracking.*
import java.util.*
import kotlin.math.round

@AndroidEntryPoint
class TrackingFragment : Fragment(R.layout.fragment_tracking), OnMapReadyCallback {

    private val viewModel: MainViewModel by viewModels()

    private var map: GoogleMap? = null

    private var isTracking = false

    private var pathPoint = mutableListOf<PolyLine>()

    private var currentTimeMillis = 0L

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mapView.onCreate(savedInstanceState)

        setListeners()
        mapView.getMapAsync(this)
        addAllPolyline()
        subscribeToObservers()

    }


    private fun setListeners() {
        btnToggleRun.setOnClickListener { toggleRun() }
        btnFinishRun.setOnClickListener {
            zoomToSeeWholeTrack()
            endRunAndSaveToDB()
        }
        imgCancelRun.setOnClickListener { showCancelRunDialog() }
    }


    private fun subscribeToObservers() {
        TrackingService.isTracking.observe(viewLifecycleOwner, Observer {
            updateTracking(it)
        })

        TrackingService.pathPoints.observe(viewLifecycleOwner, Observer {
            pathPoint = it
            addLatestPolyline()
            moveCameraToUser()
        })

        TrackingService.timeRunInMillis.observe(viewLifecycleOwner, Observer {
            currentTimeMillis = it
            imgCancelRun.isVisible = currentTimeMillis > 0L
            val formattedTime = getFormattedStopwatchTime(it, true)
            tvTimer.text = formattedTime

        })
    }


    private fun toggleRun() {
        if (isTracking) {
            sendCommandToService(ACTION_PAUSE_SERVICE)
        } else sendCommandToService(ACTION_START_OR_RESUME_SERVICE)
    }


    private fun showCancelRunDialog() {
        val dialog = MaterialAlertDialogBuilder(requireContext(), R.style.AlertDialogTheme)
            .setTitle("Cancel the run")
            .setMessage("Are you sure to cancel the run and delete all the data")
            .setIcon(R.drawable.ic_run)
            .setPositiveButton("Yes") { _, _ ->
                stopRun()
            }
            .setNegativeButton("No") { dialogInterface, _ ->
                dialogInterface.cancel()
            }
            .create()

        dialog.show()
    }


    private fun stopRun() {
        sendCommandToService(ACTION_STOP_SERVICE)
        findNavController().navigate(R.id.action_trackingFragment_to_runFragment)
    }


    @SuppressLint("SetTextI18n")
    private fun updateTracking(isTracking: Boolean) {
        this.isTracking = isTracking
        if (!isTracking) {
            btnToggleRun.text = "Start"
            btnFinishRun.isVisible = true
        } else {
            btnToggleRun.text = "Stop"
            btnFinishRun.isVisible = false
        }
    }


    private fun moveCameraToUser() {
        if (pathPoint.isNotEmpty() && pathPoint.last().isNotEmpty()) {
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    pathPoint.last().last(),
                    MAP_CAMERA_ZOOM
                )
            )
        }
    }


    private fun addAllPolyline() {
        for (polyline in pathPoint) {
            val polyLineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .addAll(polyline)
            map?.addPolyline(polyLineOptions)
        }
    }


    private fun addLatestPolyline() {
        if (pathPoint.isNotEmpty() && pathPoint.last().size > 1) {
            val preLastLatLng = pathPoint.last()[pathPoint.last().size - 2]
            val lastLatLng = pathPoint.last().last()
            val polyLineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .add(preLastLatLng)
                .add(lastLatLng)
            map?.addPolyline(polyLineOptions)
        }
    }


    private fun zoomToSeeWholeTrack() {
        val bounds = LatLngBounds.Builder()
        for (polyline in pathPoint) {
            for (pos in polyline) {
                bounds.include(pos)
            }
        }
        map?.moveCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds.build(),
                mapView.height,
                mapView.width,
                (mapView.height * 0.05f).toInt()
            )
        )
    }


    private fun endRunAndSaveToDB() {
        val weight = 80f  // dynamic
        map?.snapshot { bitmap ->
            var distanceInMeters = 0
            for (polyline in pathPoint) {
                distanceInMeters += calculatePolylineLength(polyline).toInt()
            }

            val avgSpeed =
                round(((distanceInMeters) / 1000f) / (currentTimeMillis / 1000f / 60 / 60) * 10) / 10f
            val dateTimeStamp = Calendar.getInstance().timeInMillis
            val caloriesBurned = ((distanceInMeters / 1000f) * weight).toInt()

            val run = Run(
                bitmap,
                dateTimeStamp,
                avgSpeed,
                distanceInMeters,
                currentTimeMillis,
                caloriesBurned
            )

            viewModel.inserRun(run)
            showSnack(
                requireActivity().findViewById(R.id.rootView),
                "Run saved successfully"
            )
            stopRun()
        }
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
        val indiaLatLng = LatLng(20.5937, 78.9629)
        val style = loadRawResourceStyle(requireContext(), R.raw.style_json)
        map?.setMapStyle(style)
        map?.moveCamera(CameraUpdateFactory.newLatLng(indiaLatLng))
    }
}