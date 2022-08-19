@file:JvmName("ColorStateList")

package util.mddesign.color

import android.content.res.ColorStateList
import androidx.annotation.ColorInt

fun simpleColorStateList(
    @ColorInt normalColor: Int,
    @ColorInt selectedColor: Int
): ColorStateList = ColorStateList(
    arrayOf(
        intArrayOf(-android.R.attr.state_checked),
        intArrayOf(android.R.attr.state_checked)
    ),
    intArrayOf(
        normalColor,
        selectedColor
    )
)
