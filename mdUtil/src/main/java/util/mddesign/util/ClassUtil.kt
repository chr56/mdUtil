/**
 * @author Aidan Follestad (afollestad)
 */
@file:JvmName("ClassUtil")

package util.mddesign.util

fun isInClassPath(clsName: String): Boolean {
    return try {
        inClassPath(clsName) != null
    } catch (t: Throwable) {
        false
    }
}

fun inClassPath(clsName: String): Class<*>? =
    try {
        Class.forName(clsName)
    } catch (t: Throwable) {
        throw IllegalStateException(
            "$clsName is not in your class path! You must include the associated library."
        )
    }
