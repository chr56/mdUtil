@file:JvmName("EditTextUtil")

package util.mddesign.viewtint

import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.os.Build
import android.util.Log
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.ContextCompat
import util.mddesign.R
import util.mddesign.drawable.createTintedDrawable

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

fun setCursorTint(editText: EditText, @ColorInt color: Int) {
    try {
        val fCursorDrawableRes = TextView::class.java.getDeclaredField("mCursorDrawableRes")
        fCursorDrawableRes.isAccessible = true
        val mCursorDrawableRes = fCursorDrawableRes.getInt(editText)
        val fEditor = TextView::class.java.getDeclaredField("mEditor")
        fEditor.isAccessible = true
        val editor = fEditor[editText]
        val clazz: Class<*> = editor.javaClass
        val fCursorDrawable = clazz.getDeclaredField("mCursorDrawable")
        fCursorDrawable.isAccessible = true
        val drawables = arrayOfNulls<Drawable>(2)
        drawables[0] = ContextCompat.getDrawable(editText.context, mCursorDrawableRes)
        drawables[0] = createTintedDrawable(drawables[0]!!, color)
        drawables[1] = ContextCompat.getDrawable(editText.context, mCursorDrawableRes)
        drawables[1] = createTintedDrawable(drawables[1]!!, color)
        fCursorDrawable[editor] = drawables
    } catch (e: Exception) {
        Log.v("ReflectEditText", e.message.orEmpty())
    }
}
