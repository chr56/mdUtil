/**
 * @author Aidan Follestad (afollestad)
 */
@file:JvmName("Util")

package util.mddesign.util

import android.content.Context
import androidx.annotation.AttrRes
import util.mdcolor.ColorUtil.isColorLight
import java.lang.IllegalStateException
import kotlin.jvm.JvmOverloads

fun isWindowBackgroundDark(context: Context): Boolean {
    return !isColorLight(resolveColor(context, android.R.attr.windowBackground))
}

@JvmOverloads
fun resolveColor(context: Context, @AttrRes attr: Int, fallback: Int = 0): Int {
    val a = context.theme.obtainStyledAttributes(intArrayOf(attr))
    return try {
        a.getColor(0, fallback)
    } finally {
        a.recycle()
    }
}

fun isInClassPath(clsName: String): Boolean {
    return try {
        inClassPath(clsName) != null
    } catch (t: Throwable) {
        false
    }
}

fun inClassPath(clsName: String): Class<*>? =
    try {
        Class.forName(clsName)
    } catch (t: Throwable) {
        throw IllegalStateException(
            "$clsName is not in your class path! You must include the associated library."
        )
    }
