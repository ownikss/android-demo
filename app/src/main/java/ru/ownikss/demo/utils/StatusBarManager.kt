package ru.ownikss.demo.utils

import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.View
import android.view.Window
import android.view.WindowManager
import ru.ownikss.demo.R
import kotlin.math.roundToInt

class StatusBarManager {
    companion object {

        private fun resetFlags(window: Window) {
            setWindowFlag(window, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, false)
            setWindowFlag(window, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, false)
        }

        fun getHeight(context: Context?): Int {
            if (context != null) {
                val resourceId = context.resources.getIdentifier("status_bar_height", "dimen", "android")
                return context.resources.getDimension(resourceId)
                    .roundToInt()
            }
            return 0
        }

        fun setTranslucent(window: Window) {
            resetFlags(window)
            setWindowFlag(window, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, true)
        }

        fun setDarkTransparent(window: Window) {
            resetFlags(window)
            setWindowFlag(window, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, true)
            setWindowFlag(window, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
            setWindowFlag(window, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, false)
        }

        fun setDarkContent(window: Window) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR or window.decorView.systemUiVisibility
            }
        }

        fun setLightContent(window: Window) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val isDark = (View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR and window.decorView.systemUiVisibility) == View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                if (isDark) {
                    window.decorView.systemUiVisibility = window.decorView.systemUiVisibility xor View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
                }
            }
        }

        fun setAccentStatusBar(window: Window, resources: Resources) {
            resetFlags(window)
            window.statusBarColor = resources.getColor(R.color.colorAccent)
        }

        fun setColor(window: Window, color: Int) {
            window.statusBarColor = color
        }

        private fun setWindowFlag(window: Window, bits: Int, on: Boolean) {
            val winParams = window.attributes
            if (on) {
                winParams.flags = winParams.flags or bits
            } else {
                winParams.flags = winParams.flags and bits.inv()
            }
            window.attributes = winParams
        }
    }
}