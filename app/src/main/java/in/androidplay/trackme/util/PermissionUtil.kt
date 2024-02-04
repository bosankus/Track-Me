package `in`.androidplay.trackme.util

import android.content.Context
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher
import androidx.core.content.ContextCompat
import `in`.androidplay.trackme.util.UIHelper.openPermissionSettingsPage

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 8/1/2020, 9:12 PM
 */
internal object PermissionUtil {

    // Check single permission availability
    fun hasPermission(
        context: Context,
        permission: String,
    ): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            permission
        ) == PackageManager.PERMISSION_GRANTED;
    }

    // request permission from permission lists
    fun askForPermissions(
        context: Context,
        availableListOfPermissions: Array<String>,
        generatedPermissionList: MutableList<String>,
        multiplePermissionLauncher: ActivityResultLauncher<Array<String>>,
    ) {
        val newPermissionStr = Array(generatedPermissionList.size) { "" }
        for (i in newPermissionStr.indices) {
            newPermissionStr[i] = availableListOfPermissions[i]
        }
        if (newPermissionStr.isNotEmpty()) {
            multiplePermissionLauncher.launch(newPermissionStr)
        } else {
            /* User has pressed 'Deny & Don't ask again' so we have to show the enable permissions dialog
        which will lead them to app details page to enable permissions from there. */
            UIHelper.showAlert(
                context,
                Constants.PERMISSION_REQUEST_RATIONAL,
            ) { context.openPermissionSettingsPage() }
        }
    }
}
