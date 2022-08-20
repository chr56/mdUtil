@file:JvmName("EditTextUtil")

package util.mddesign.viewtint

import android.content.res.ColorStateList
import android.os.Build
import android.widget.EditText
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import util.mddesign.R
import util.mddesign.util.TintHelper.setCursorTint

fun EditText.setTint(@ColorInt color: Int, useDarker: Boolean) {
    val editTextColorStateList = ColorStateList(
        arrayOf(
            intArrayOf(
                -android.R.attr.state_enabled
            ),
            intArrayOf(
                android.R.attr.state_enabled,
                -android.R.attr.state_pressed,
                -android.R.attr.state_focused
            ),
            intArrayOf()
        ),
        intArrayOf(
            ContextCompat.getColor(
                context,
                if (useDarker) R.color.MD_text_disabled_dark else R.color.MD_text_disabled_light
            ),
            ContextCompat.getColor(
                context,
                if (useDarker) R.color.MD_control_normal_dark else R.color.MD_control_normal_light
            ),
            color
        )
    )
    if (this is AppCompatEditText) {
        supportBackgroundTintList = editTextColorStateList
    } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        backgroundTintList = editTextColorStateList
    }
    setCursorTint(this, color)
}
