package `in`.androidplay.trackme.util

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar
import `in`.androidplay.trackme.util.Constants.GLOBAL_TAG

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 7/30/2020, 10:54 PM
 */
object UIHelper {

    fun logMessage(message: String) {
        Log.d(GLOBAL_TAG, message)
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnack(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }

    fun showSnackWithActionButton(
        view: View,
        message: String,
        length: Int,
        btnText: String,
        btnAction: () -> Unit,
    ) {
        Snackbar.make(view, message, length)
            .setAction(btnText) { btnAction() }
            .show()
    }

    fun showAlert(
        context: Context,
        message: String,
        positiveAction: () -> Unit,
    ) {
        MaterialAlertDialogBuilder(context)
            .setTitle(message)
            .setPositiveButton("Ok") { _, _ -> positiveAction() }
            .create()
            .show()
    }

    fun Context.openPermissionSettingsPage() {
        val packageName = packageName
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
        val uri = Uri.fromParts("package", packageName, null)
        intent.data = uri
        startActivity(intent)
    }
}