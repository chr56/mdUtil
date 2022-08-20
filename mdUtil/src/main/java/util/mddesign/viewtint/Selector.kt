@file:JvmName("SelectorUtil")
package util.mddesign.viewtint

import android.content.res.ColorStateList
import android.graphics.drawable.RippleDrawable
import android.os.Build
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.google.android.material.floatingactionbutton.FloatingActionButton
import util.mdcolor.ColorUtil
import util.mddesign.R
import util.mddesign.color.defaultRippleColor
import util.mddesign.color.disabledColorStateList
import util.mddesign.drawable.createTintedDrawable
import util.mddesign.util.setBackgroundCompat

fun setTintSelector(view: View, @ColorInt color: Int, darker: Boolean, useDarkTheme: Boolean) {
    val isColorLight = ColorUtil.isColorLight(color)
    val disabled =
        ContextCompat.getColor(
            view.context,
            if (useDarkTheme) R.color.MD_button_disabled_dark else R.color.MD_button_disabled_light
        )
    val pressed = ColorUtil.shiftColor(color, if (darker) 0.9f else 1.1f)
    val activated = ColorUtil.shiftColor(color, if (darker) 1.1f else 0.9f)
    val rippleColor = defaultRippleColor(view.context, isColorLight)
    val textColor =
        ContextCompat.getColor(
            view.context,
            if (isColorLight) R.color.MD_primary_text_light else R.color.MD_primary_text_dark
        )
    val sl: ColorStateList

    when (view) {
        is Button -> {
            sl = disabledColorStateList(color, disabled)
            if (view.getBackground() is RippleDrawable &&
                Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP
            ) {
                val rd = view.getBackground() as RippleDrawable
                rd.setColor(ColorStateList.valueOf(rippleColor))
            }

            // Disabled text color state for buttons, may get overridden later by ATE tags
            view.setTextColor(
                disabledColorStateList(
                    textColor,
                    ContextCompat.getColor(
                        view.getContext(),
                        if (useDarkTheme) R.color.MD_button_text_disabled_dark else R.color.MD_button_text_disabled_light
                    )
                )
            )
        }
        is FloatingActionButton -> {
            // FloatingActionButton doesn't support disabled state?
            sl = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_pressed),
                    intArrayOf(android.R.attr.state_pressed)
                ),
                intArrayOf(
                    color,
                    pressed
                )
            )
            view.rippleColor = rippleColor
            view.backgroundTintList = sl
            if (view.drawable != null) view.setImageDrawable(
                createTintedDrawable(
                    view.drawable,
                    textColor
                )
            )
            return
        }
        else -> {
            sl = ColorStateList(
                arrayOf(
                    intArrayOf(-android.R.attr.state_enabled),
                    intArrayOf(android.R.attr.state_enabled),
                    intArrayOf(android.R.attr.state_enabled, android.R.attr.state_pressed),
                    intArrayOf(android.R.attr.state_enabled, android.R.attr.state_activated),
                    intArrayOf(android.R.attr.state_enabled, android.R.attr.state_checked)
                ),
                intArrayOf(
                    disabled,
                    color,
                    pressed,
                    activated,
                    activated
                )
            )
        }
    }
    var drawable = view.background
    if (drawable != null) {
        drawable = createTintedDrawable(drawable, sl)
        setBackgroundCompat(view, drawable)
    }
    if (view is TextView && view !is Button) {
        view.setTextColor(
            disabledColorStateList(
                textColor,
                ContextCompat.getColor(
                    view.getContext(),
                    if (isColorLight) R.color.MD_text_disabled_light else R.color.MD_text_disabled_dark
                )
            )
        )
    }
}