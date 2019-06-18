package es.plexus.android.plexuschuck.datalayer.utils

import android.content.Context
import android.net.ConnectivityManager

/**
 * This file relates to utility functions to be used across the whole project.
 *
 * @author pablol.
 * @since 1.0
 */
fun isNetworkConnected(context: Context): Boolean {
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return cm.activeNetworkInfo != null
}
