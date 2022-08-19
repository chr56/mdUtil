/**
 * @author Karim Abou Zeid (kabouzeid)
 */
@file:JvmName("NavigationViewUtil")

package util.mddesign.util

import androidx.annotation.ColorInt
import com.google.android.material.navigation.NavigationView
import util.mddesign.color.simpleColorStateList

fun NavigationView.setItemIconColors(
    @ColorInt normalColor: Int,
    @ColorInt selectedColor: Int
) {
    itemIconTintList = simpleColorStateList(normalColor, selectedColor)
}

fun NavigationView.setItemTextColors(
    @ColorInt normalColor: Int,
    @ColorInt selectedColor: Int
) {
    itemTextColor = simpleColorStateList(normalColor, selectedColor)
}
