@file:JvmName("RadioButtonUtil")

package util.mddesign.viewtint

import android.content.res.ColorStateList
import android.widget.RadioButton
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import util.mdcolor.ColorUtil
import util.mddesign.R

fun RadioButton.setTint(@ColorInt color: Int, useDarker: Boolean) {
    val sl = ColorStateList(
        arrayOf(
            intArrayOf(-android.R.attr.state_enabled),
            intArrayOf(
                android.R.attr.state_enabled,
                -android.R.attr.state_checked
            ),
            intArrayOf(
                android.R.attr.state_enabled,
                android.R.attr.state_checked
            )
        ),
        intArrayOf(
            // Radio button includes own alpha for disabled state
            ColorUtil.stripAlpha(
                ContextCompat.getColor(
                    context,
                    if (useDarker) R.color.MD_control_disabled_dark else R.color.MD_control_disabled_light
                )
            ),
            ContextCompat.getColor(
                context,
                if (useDarker) R.color.MD_control_normal_dark else R.color.MD_control_normal_light
            ),
            color
        )
    )
    buttonTintList = sl
}
