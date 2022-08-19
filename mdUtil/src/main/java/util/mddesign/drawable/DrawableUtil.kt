@file:JvmName("DrawableUtil")

package util.mddesign.drawable

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import util.mdcolor.ColorUtil
import util.mdcolor.ColorUtil.adjustAlpha
import util.mddesign.R

@CheckResult
fun createTintedDrawable(source: Drawable, sl: ColorStateList): Drawable? {
    val drawable = DrawableCompat.wrap(source.mutate()).apply {
        DrawableCompat.setTintList(this, sl)
    }
    return drawable
}

@CheckResult
fun createTintedDrawable(source: Drawable, @ColorInt color: Int): Drawable? {
    val drawable = DrawableCompat.wrap(source.mutate()).apply {
        DrawableCompat.setTintMode(this, PorterDuff.Mode.SRC_IN)
        DrawableCompat.setTint(this, color)
    }
    return drawable
}

fun createTransitionDrawable(
    @ColorInt startColor: Int,
    @ColorInt endColor: Int
): TransitionDrawable =
    createTransitionDrawable(ColorDrawable(startColor), ColorDrawable(endColor))

fun createTransitionDrawable(
    start: Drawable?,
    end: Drawable?
): TransitionDrawable =
    TransitionDrawable(
        arrayOf(start, end)
    )

internal fun modifySwitchDrawable(
    context: Context,
    from: Drawable,
    @ColorInt tintColor: Int,
    thumb: Boolean,
    compatSwitch: Boolean,
    useDarker: Boolean
): Drawable? {
    val tint =
        if (useDarker) {
            ColorUtil.shiftColor(tintColor, 1.1f)
        } else {
            tintColor
        }.apply {
            adjustAlpha(this, if (compatSwitch && !thumb) 0.5f else 1.0f)
        }
    val disabled: Int =
        if (thumb) {
            ContextCompat.getColor(
                context,
                if (useDarker) R.color.MD_switch_thumb_disabled_dark else R.color.MD_switch_thumb_disabled_light
            )
        } else {
            ContextCompat.getColor(
                context,
                if (useDarker) R.color.MD_switch_track_disabled_dark else R.color.MD_switch_track_disabled_light
            )
        }
    val normal: Int =
        if (thumb) {
            ContextCompat.getColor(
                context,
                if (useDarker) R.color.MD_switch_thumb_normal_dark else R.color.MD_switch_thumb_normal_light
            )
        } else {
            ContextCompat.getColor(
                context,
                if (useDarker) R.color.MD_switch_track_normal_dark else R.color.MD_switch_track_normal_light
            )
        }.apply {
            if (!compatSwitch) {
                ColorUtil.stripAlpha(this)
            }
        }
    val colorStateList = ColorStateList(
        arrayOf(
            intArrayOf(
                -android.R.attr.state_enabled
            ),
            intArrayOf(
                android.R.attr.state_enabled,
                -android.R.attr.state_activated,
                -android.R.attr.state_checked
            ),
            intArrayOf(
                android.R.attr.state_enabled,
                android.R.attr.state_activated
            ),
            intArrayOf(
                android.R.attr.state_enabled,
                android.R.attr.state_checked
            )
        ),
        intArrayOf(
            disabled,
            normal,
            tint,
            tint
        )
    )
    return createTintedDrawable(from, colorStateList)
}
