package com.vikayarska.kotlinapplicationcompose.presentation.ui.customview

import android.content.Context
import android.view.ViewOutlineProvider
import androidx.appcompat.widget.AppCompatImageView
import com.vikayarska.kotlinapplicationcompose.R

class CircleImageView(
    context: Context
) : AppCompatImageView(context) {

    init {
        outlineProvider = ViewOutlineProvider.BACKGROUND
        clipToOutline = true
        setBackgroundResource(R.drawable.ic_circle_background)
        scaleType = ScaleType.CENTER_CROP
    }
}