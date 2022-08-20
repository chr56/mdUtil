package util.mddesign.color

import android.R
import android.content.Context
import androidx.annotation.AttrRes
import util.mdcolor.ColorUtil

@JvmOverloads
fun resolveColor(context: Context, @AttrRes attr: Int, fallback: Int = 0): Int {
    val a = context.theme.obtainStyledAttributes(intArrayOf(attr))
    return try {
        a.getColor(0, fallback)
    } finally {
        a.recycle()
    }
}

fun isWindowBackgroundDark(context: Context): Boolean {
    return !ColorUtil.isColorLight(resolveColor(context, R.attr.windowBackground))
}