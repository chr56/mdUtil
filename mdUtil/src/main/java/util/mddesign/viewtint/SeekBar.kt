@file:JvmName("SeekBarUtil")

package util.mddesign.viewtint

import android.os.Build
import android.widget.SeekBar
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import util.mddesign.R
import util.mddesign.color.disabledColorStateList

fun SeekBar.setTint(@ColorInt color: Int, useDarker: Boolean) {
    val colorStateList =
        disabledColorStateList(
            color,
            ContextCompat.getColor(
                context,
                if (useDarker) R.color.MD_control_disabled_dark else R.color.MD_control_disabled_light
            )
        )
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
        thumbTintList = colorStateList
        progressTintList = colorStateList
    } else {
        val colorFilter =
            BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                color,
                BlendModeCompat.SRC_IN
            )
        if (indeterminateDrawable != null) {
            indeterminateDrawable.colorFilter = colorFilter
        }
        if (progressDrawable != null) {
            progressDrawable.colorFilter = colorFilter
        }
    }
}
