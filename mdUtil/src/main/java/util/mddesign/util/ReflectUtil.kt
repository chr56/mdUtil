package util.mddesign.util

import kotlin.jvm.Throws

@Throws(NoSuchFieldException::class, SecurityException::class)
internal inline fun <T, reified F> T.reflectField(clazz: Class<T>, fieldName: String): F {
    val f = clazz.getDeclaredField(fieldName)
    f.isAccessible = true
    return f.get(this) as F
}
