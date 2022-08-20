@file:JvmName("SearchViewTintUtil")

package util.mddesign.viewtint

import android.widget.EditText
import android.widget.ImageView
import android.widget.SearchView
import androidx.annotation.ColorInt
import util.mdcolor.ColorUtil

fun setSearchViewContentColor(searchView: SearchView?, @ColorInt color: Int) {
    if (searchView != null) {
        try {
            searchView.apply {
                tintImageView("mSearchButton", color)
                tintImageView("mGoButton", color)
                tintImageView("mCloseButton", color)
                tintImageView("mVoiceButton", color)
                tintTextView(color)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}

internal fun SearchView.tintTextView(@ColorInt color: Int) {
    val mSearchSrcTextViewField = this.javaClass.getDeclaredField("mSearchSrcTextView")
    mSearchSrcTextViewField.isAccessible = true
    val mSearchSrcTextView = mSearchSrcTextViewField[this] as EditText
    mSearchSrcTextView.setTextColor(color)
    mSearchSrcTextView.setHintTextColor(ColorUtil.adjustAlpha(color, 0.5f))
    setCursorTint(mSearchSrcTextView, color)
}

internal fun SearchView.tintImageView(fieldName: String, @ColorInt color: Int) {
    val field = this.javaClass.getDeclaredField(fieldName)
    field.isAccessible = true
    val imageView = field[this] as ImageView
    imageView.setDrawableColor(color)
}
