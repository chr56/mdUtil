@file:JvmName("RippleColor")
package util.mddesign.color

import android.content.Context
import androidx.annotation.ColorInt
import androidx.appcompat.R
import androidx.core.content.ContextCompat

@ColorInt
internal fun defaultRippleColor(context: Context, useDarkRipple: Boolean): Int {
    // Light ripple is actually translucent black, and vice versa
    return ContextCompat.getColor(
        context,
        if (useDarkRipple) R.color.ripple_material_light else R.color.ripple_material_dark
    )
}
