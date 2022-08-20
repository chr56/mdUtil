/**
 * @author Karim Abou Zeid (kabouzeid)
 */
@file:JvmName("TextColor")

package util.mddesign.color

import android.content.Context
import androidx.annotation.ColorInt
import androidx.appcompat.R
import androidx.core.content.ContextCompat

@ColorInt
fun getPrimaryTextColor(context: Context, dark: Boolean): Int {
    return if (dark) {
        ContextCompat.getColor(context, R.color.primary_text_default_material_light)
    } else {
        ContextCompat.getColor(context, R.color.primary_text_default_material_dark)
    }
}

@ColorInt
fun getSecondaryTextColor(context: Context, dark: Boolean): Int {
    return if (dark) {
        ContextCompat.getColor(context, R.color.secondary_text_default_material_light)
    } else {
        ContextCompat.getColor(context, R.color.secondary_text_default_material_dark)
    }
}

@ColorInt
fun getPrimaryDisabledTextColor(context: Context, dark: Boolean): Int {
    return if (dark) {
        ContextCompat.getColor(context, R.color.primary_text_disabled_material_light)
    } else {
        ContextCompat.getColor(context, R.color.primary_text_disabled_material_dark)
    }
}

@ColorInt
fun getSecondaryDisabledTextColor(context: Context, dark: Boolean): Int {
    return if (dark) {
        ContextCompat.getColor(context, R.color.secondary_text_disabled_material_light)
    } else {
        ContextCompat.getColor(context, R.color.secondary_text_disabled_material_dark)
    }
}
