@file:JvmName("TintHelper")

package util.mddesign.util

import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.view.View
import android.widget.*
import androidx.annotation.ColorInt
import androidx.appcompat.widget.SwitchCompat
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import util.mdcolor.ColorUtil
import util.mddesign.color.isWindowBackgroundDark
import util.mddesign.drawable.createTintedDrawable
import util.mddesign.viewtint.setDrawableColor
import util.mddesign.viewtint.setTint
import util.mddesign.viewtint.setTintSelector

/**
 * @author afollestad, plusCubed
 */

/**
 * @param tintBackground need to tint the background of a view or not
 * @param isDark isWindowBackgroundDark
 */
@JvmOverloads
@JvmName("setTintAuto")
fun View.tint(
    @ColorInt color: Int,
    tintBackground: Boolean,
    isDark: Boolean = isWindowBackgroundDark(context)
) {
    if (!tintBackground) {
        var tintRipples = true
        when (this) {
            is RadioButton -> this.setTint(color, isDark)
            is SeekBar -> this.setTint(color, isDark)
            is ProgressBar -> this.setTint(color, isDark)
            is EditText -> this.setTint(color, isDark)
            is CheckBox -> this.setTint(color, isDark)
            is ImageView -> this.setDrawableColor(color)
            is Switch -> this.setTint(color, isDark)
            is SwitchCompat -> this.setTint(color, isDark)
            else -> {
                // no ripples
                tintRipples = false
            }
        }

        // Ripples for the above views (e.g. when you tap and hold a switch or checkbox)
        if (background is RippleDrawable && tintRipples &&
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
        ) {
            val unchecked =
                ContextCompat.getColor(
                    context,
                    if (isDark) androidx.appcompat.R.color.ripple_material_dark
                    else androidx.appcompat.R.color.ripple_material_light
                )
            val checked = ColorUtil.adjustAlpha(color, 0.4f)
            val colorStateList =
                ColorStateList(
                    arrayOf(
                        intArrayOf(
                            -android.R.attr.state_activated,
                            -android.R.attr.state_checked
                        ),
                        intArrayOf(android.R.attr.state_activated),
                        intArrayOf(android.R.attr.state_checked)
                    ),
                    intArrayOf(
                        unchecked,
                        checked,
                        checked
                    )
                )
            (background as RippleDrawable).setColor(colorStateList)
        }
    }
    if (tintBackground) {
        if (this is FloatingActionButton || this is Button) {
            setTintSelector(this, color, false, isDark)
        } else if (this.background != null) {
            var drawable = this.background
            if (drawable != null) {
                drawable = createTintedDrawable(drawable, color)
                setBackgroundCompat(this, drawable)
            }
        }
    }
}
