package com.testtask.androidassignmentone.utils

import android.content.Context
import com.testtask.androidassignmentone.R

object ViewUtils {
    fun isTablet(context: Context): Boolean {
        return context.resources.getBoolean(R.bool.isTablet)
    }
}