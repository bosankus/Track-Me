package `in`.androidplay.trackme.ui.activity

import `in`.androidplay.trackme.R
import `in`.androidplay.trackme.databinding.ActivityMainBinding
import `in`.androidplay.trackme.util.Constants.ACTION_SHOW_TRACKING_FRAGMENT
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.rootView)

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
        binding.bottomNavigationView.setupWithNavController(findNavController(R.id.navHostFragment))

        findNavController(R.id.navHostFragment)
            .addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.settingsFragment, R.id.runFragment, R.id.statisticsFragment ->
                        binding.bottomNavigationView.visibility = View.VISIBLE
                    else -> binding.bottomNavigationView.visibility = View.GONE
                }
            }
    }

    private fun navigateToTrackingFragmentWhenNeeded(intent: Intent?) {
        intent?.let {
            if (it.action == ACTION_SHOW_TRACKING_FRAGMENT) {
                findNavController(R.id.navHostFragment).navigate(R.id.action_global_trackingFragment)
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTrackingFragmentWhenNeeded(intent)
    }
}