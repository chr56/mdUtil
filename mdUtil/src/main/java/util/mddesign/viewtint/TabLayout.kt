@file:JvmName("TabLayoutUtil")

package util.mddesign.viewtint

import androidx.annotation.ColorInt
import com.google.android.material.tabs.TabLayout
import util.mddesign.color.selectableColorStateList
import util.mddesign.drawable.createTintedDrawable

/**
 * @author Karim Abou Zeid (kabouzeid)
 */
fun TabLayout?.setTabIconColors(
    @ColorInt normalColor: Int,
    @ColorInt selectedColor: Int
) {
    if (this != null) {
        val colorStateList = selectableColorStateList(normalColor, selectedColor)
        for (i in 0 until tabCount) {
            val tab = this.getTabAt(i)
            if (tab != null && tab.icon != null) {
                tab.icon = createTintedDrawable(tab.icon!!, colorStateList)
            }
        }
    }
}
