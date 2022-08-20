package util.mddesign.util

import kotlin.jvm.Throws

@Throws(NoSuchFieldException::class, SecurityException::class)
internal inline fun <reified T, reified F> T.reflectDeclaredField(fieldName: String): F {
    val f = T::class.java.getDeclaredField(fieldName)
    f.isAccessible = true
    return f.get(this) as F
}
