package com.vikayarska.kotlinapplicationcompose.presentation.fragments

import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.vikayarska.kotlinapplicationcompose.R

fun Fragment.showError(message: String) {
    val alertDialogBuilder = AlertDialog.Builder(this.requireContext())

    alertDialogBuilder.apply {
        setTitle(R.string.error)
        setMessage(message)
        setNegativeButton(R.string.ok) { dialog, _ ->
            dialog.dismiss()
        }
    }.create().show()

}