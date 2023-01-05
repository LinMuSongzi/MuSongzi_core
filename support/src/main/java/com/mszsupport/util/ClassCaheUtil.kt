package com.mszsupport.util

import androidx.collection.SimpleArrayMap
import java.lang.reflect.Constructor
import java.lang.reflect.Field
import java.lang.reflect.Method

object ClassCaheUtil {

    private val CACHE_CLASS_MAP = SimpleArrayMap<String, Class<*>?>()
    private val CACHE_FIELD_MAP = SimpleArrayMap<String, Field?>()
    private val CACHE_METHOD_MAP = SimpleArrayMap<String, Method?>()
    private val CACHE_CONSTRUCTOR_MAP = SimpleArrayMap<String, Constructor<*>?>()

    fun String.getClassCache() = CACHE_CLASS_MAP.get(this)
    fun String.getFieldCache() = CACHE_FIELD_MAP.get(this)
    fun String.getMethodCache() = CACHE_METHOD_MAP.get(this)
    fun String.getConstructorCache() = CACHE_CONSTRUCTOR_MAP.get(this)

    fun String.putClassCache(clazz: Class<*>) = CACHE_CLASS_MAP.put(this, clazz)
    fun String.putFieldCache(field: Field) = CACHE_FIELD_MAP.put(this, field)
    fun String.putMethodCache(method: Method) = CACHE_METHOD_MAP.put(this, method)
    fun String.putClassConstructorCache(constructor: Constructor<*>) =
        CACHE_CONSTRUCTOR_MAP.put(this, constructor)

    fun <F> String.getFieldInstance(obj: Any?): F? {
        return getFieldCache()?.getInt(obj) as? F
    }

//    fun <F> String.getMethodInstance(obj: Any?): F? {
//        return getMethodCache()?.g(obj) as? F
//    }

}