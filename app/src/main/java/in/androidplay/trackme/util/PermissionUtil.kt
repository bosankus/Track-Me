package `in`.androidplay.trackme.util

import `in`.androidplay.trackme.util.Constants.BACKGROUND_LOCATION
import `in`.androidplay.trackme.util.Constants.COARSE_LOCATION
import `in`.androidplay.trackme.util.Constants.FINE_LOCATION
import `in`.androidplay.trackme.util.Constants.PERMISSION_REQUEST_CODE
import `in`.androidplay.trackme.util.Constants.PERMISSION_REQUEST_RATIONAL
import android.content.Context
import android.os.Build
import androidx.fragment.app.Fragment
import pub.devrel.easypermissions.EasyPermissions

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 8/1/2020, 9:12 PM
 */
object PermissionUtil {

    fun hasLocationPermission(context: Context) =
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            EasyPermissions.hasPermissions(
                context,
                FINE_LOCATION,
                COARSE_LOCATION
            )
        } else {
            EasyPermissions.hasPermissions(
                context,
                FINE_LOCATION,
                COARSE_LOCATION,
                BACKGROUND_LOCATION
            )
        }

    fun askPermissions(fragment: Fragment) {
        if (hasLocationPermission(fragment.requireContext())) {
            return
        } else {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                EasyPermissions.requestPermissions(
                    fragment,
                    PERMISSION_REQUEST_RATIONAL,
                    PERMISSION_REQUEST_CODE,
                    FINE_LOCATION,
                    COARSE_LOCATION
                )
            } else {
                EasyPermissions.requestPermissions(
                    fragment,
                    PERMISSION_REQUEST_RATIONAL,
                    PERMISSION_REQUEST_CODE,
                    FINE_LOCATION,
                    COARSE_LOCATION,
                    BACKGROUND_LOCATION
                )
            }
        }
    }
}