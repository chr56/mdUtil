@file:JvmName("CheckBoxUtil")

package util.mddesign.viewtint

import android.content.res.ColorStateList
import android.os.Build
import android.widget.CheckBox
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import util.mddesign.R
import util.mddesign.drawable.createTintedDrawable

fun CheckBox.setTint(@ColorInt color: Int, useDarker: Boolean) {
    val colorStateList = ColorStateList(
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
            ContextCompat.getColor(
                context,
                if (useDarker) R.color.MD_control_disabled_dark else R.color.MD_control_disabled_light
            ),
            ContextCompat.getColor(
                context,
                if (useDarker) R.color.MD_control_normal_dark else R.color.MD_control_normal_light
            ),
            color
        )
    )
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        buttonTintList = colorStateList
    } else {
        buttonDrawable =
            createTintedDrawable(
                ContextCompat.getDrawable(
                    context,
                    androidx.appcompat.R.drawable.abc_btn_check_material
                )!!,
                colorStateList
            )
    }
}
