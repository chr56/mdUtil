@file:JvmName("ToolbarUtil")

package util.mddesign.viewtint

import android.content.Context
import android.view.Menu
import androidx.annotation.ColorInt
import androidx.appcompat.widget.Toolbar
import util.mddesign.color.toolbarContentColor
import util.mddesign.color.toolbarSubtitleColor
import util.mddesign.color.toolbarTitleColor
import util.mddesign.drawable.createTintedDrawable

/**
 * @author Karim Abou Zeid (kabouzeid), chr_56
 */
fun Toolbar.setToolbarColor(
    context: Context,
    menu: Menu?,
    @ColorInt toolbarColor: Int,
    @ColorInt titleTextColor: Int,
    @ColorInt subtitleTextColor: Int
) {
    setToolbarColor(
        context,
        this,
        menu,
        toolbarColor,
        titleTextColor,
        subtitleTextColor
    )
}

fun setToolbarColor(
    context: Context,
    toolbar: Toolbar?,
    menu: Menu?,
    @ColorInt toolbarColor: Int,
    @ColorInt titleTextColor: Int,
    @ColorInt subtitleTextColor: Int
) {
    if (toolbar != null) {
        // Text
        toolbar.setTitleTextColor(titleTextColor)
        toolbar.setSubtitleTextColor(subtitleTextColor)
        // Icon
        if (toolbar.navigationIcon != null) {
            // Tint the toolbar navigation icon (e.g. back, drawer, etc.)
            toolbar.navigationIcon =
                createTintedDrawable(toolbar.navigationIcon!!, toolbarColor)
        }
    }
}

fun setToolbarColorAuto(
    context: Context,
    toolbar: Toolbar,
    menu: Menu?,
    @ColorInt toolbarColor: Int
) {
    setToolbarColor(
        context,
        toolbar,
        menu,
        toolbarContentColor(context, toolbarColor),
        toolbarTitleColor(context, toolbarColor),
        toolbarSubtitleColor(context, toolbarColor)
    )
}
