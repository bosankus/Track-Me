package `in`.androidplay.trackme.util

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.Toast
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Androidplay
 * Author: Ankush
 * On: 7/30/2020, 10:54 PM
 */
object Helper {

    fun logMessage(message: String, tag: String = "Basic") {
        Log.d(tag, message)
    }

    fun showToast(context: Context, message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    fun showSnack(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_LONG).show()
    }
}