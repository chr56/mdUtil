@file:JvmName("DrawableUtil")

package util.mddesign.drawable

import android.content.res.ColorStateList
import android.graphics.PorterDuff
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.TransitionDrawable
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.core.graphics.drawable.DrawableCompat

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