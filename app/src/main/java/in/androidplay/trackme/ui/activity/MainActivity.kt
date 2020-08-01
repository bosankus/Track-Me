package `in`.androidplay.trackme.ui.activity

import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.util.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigateToTrackingFragmentWhenNeeded(intent)

        supportActionBar?.hide()

        setUpWindow()

        setUpFragments()
    }


    private fun setUpWindow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.setDecorFitsSystemWindows(false)
        } else {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            window.statusBarColor = Color.WHITE
            window.navigationBarColor = Color.WHITE
        }
    }

    private fun setUpFragments() {
        bottomNavigationView.setupWithNavController(navHostFragment.findNavController())

        navHostFragment.findNavController()
            .addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.settingsFragment, R.id.runFragment, R.id.statisticsFragment ->
                        bottomNavigationView.visibility = View.VISIBLE
                    else -> bottomNavigationView.visibility = View.GONE
                }
            }
    }

    private fun navigateToTrackingFragmentWhenNeeded(intent: Intent?) {
        intent?.let {
            if (it.action == ACTION_SHOW_TRACKING_FRAGMENT) {
                navHostFragment.findNavController().navigate(R.id.action_global_trackingFragment)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTrackingFragmentWhenNeeded(intent)
    }
}