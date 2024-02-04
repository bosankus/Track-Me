package `in`.androidplay.trackme.ui.activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import dagger.hilt.android.AndroidEntryPoint
import `in`.androidplay.trackme.databinding.ActivityMainBinding
import `in`.androidplay.trackme.ui.navigation.AppNavigation
import `in`.androidplay.trackme.ui.theme.TrackMeTheme
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var binding: ActivityMainBinding

    @set:Inject
    var isFirstAppOpen = true

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.rootView)

        // navigation to global action intent
        // navigateToTrackingFragmentWhenNeeded(intent)

        // setting up nav graph
        // setUpFragments()

        // setting up composable
        setupUi()
    }

    private fun setupUi() {
        setContent {
            TrackMeTheme {
                Surface {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        AppNavigation(isFirstAppOpen = isFirstAppOpen)
                    }
                }
            }
        }
    }

    /*private fun setUpFragments() {
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        val navController = navHostFragment.navController
        binding.bottomNavigationView.setupWithNavController(navController)
        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.settingsFragment, R.id.runFragment, R.id.statisticsFragment ->
                    binding.bottomNavigationView.visibility = View.VISIBLE

                else -> binding.bottomNavigationView.visibility = View.GONE
            }
        }
    }*/

    /*private fun navigateToTrackingFragmentWhenNeeded(intent: Intent?) {
        intent?.let {
            if (it.action == ACTION_SHOW_TRACKING_FRAGMENT) {
                findNavController(R.id.navHostFragment).navigate(R.id.action_global_trackingFragment)
            }
        }
    }*/

    /*override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        navigateToTrackingFragmentWhenNeeded(intent)
    }*/
}