@file:JvmName("ToolbarColor")

package util.mddesign.color

import android.content.Context
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import util.mdcolor.ColorUtil

@CheckResult
@ColorInt
fun toolbarContentColor(context: Context, @ColorInt toolbarColor: Int): Int {
    return if (ColorUtil.isColorLight(toolbarColor)) {
        toolbarSubtitleColor(context, toolbarColor)
    } else {
        toolbarTitleColor(context, toolbarColor)
    }
}

@CheckResult
@ColorInt
fun toolbarSubtitleColor(context: Context, @ColorInt toolbarColor: Int) =
    getSecondaryTextColor(context, ColorUtil.isColorLight(toolbarColor))

@CheckResult
@ColorInt
fun toolbarTitleColor(context: Context, @ColorInt toolbarColor: Int) =
    getPrimaryTextColor(context, ColorUtil.isColorLight(toolbarColor))
