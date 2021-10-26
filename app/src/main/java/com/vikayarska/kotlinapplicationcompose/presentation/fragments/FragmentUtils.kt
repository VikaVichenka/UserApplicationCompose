package com.vikayarska.kotlinapplicationcompose.presentation.fragments

import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.ViewGroup.LayoutParams.WRAP_CONTENT
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.google.android.material.progressindicator.CircularProgressIndicator
import com.vikayarska.kotlinapplicationcompose.R

fun Fragment.showError(message: String?) {
    val alertDialogBuilder = AlertDialog.Builder(this.requireContext())

    alertDialogBuilder.apply {
        setTitle(R.string.error)
        setMessage(message ?: getString(R.string.unknown_error_message))
        setNegativeButton(R.string.ok) { dialog, _ ->
            dialog.dismiss()
        }
    }.create().show()

}

fun Fragment.showLoading(visibility: Visibility) {
    val container = this.view as ViewGroup
    var progress: View? = container.findViewById<ConstraintLayout>(R.id.container_progress_bar)
    if (progress == null) {
        progress = layoutInflater.inflate(R.layout.progress_bar, null)
        container.addView(progress, ViewGroup.LayoutParams(MATCH_PARENT, MATCH_PARENT))
    }
    progress?.visibility = visibility.value
}

enum class Visibility(val value: Int) {
    Show(View.VISIBLE),
    Hide(View.GONE)
}