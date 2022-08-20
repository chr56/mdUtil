@file:JvmName("ImageViewUtil")

package util.mddesign.viewtint

import android.widget.ImageView
import androidx.annotation.ColorInt
import util.mddesign.drawable.createTintedDrawable

fun ImageView.setDrawableColor(@ColorInt color: Int) {
    if (drawable != null) {
        setImageDrawable(
            createTintedDrawable(drawable!!, color)
        )
    }
}
