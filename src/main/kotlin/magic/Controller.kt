package com.arjix.magic

import com.arjix.http.HttpVerb
import java.lang.reflect.Method

@Target(AnnotationTarget.CLASS) @Retention(AnnotationRetention.RUNTIME)
annotation class Controller(val path: String)

@Target(AnnotationTarget.FUNCTION) @Retention(AnnotationRetention.RUNTIME)
annotation class HttpMapping(val method: HttpVerb, val path: String)

@Target(AnnotationTarget.VALUE_PARAMETER) @Retention(AnnotationRetention.RUNTIME)
annotation class PathVariable(val name: String)

fun <T: Annotation> Class<*>.getMethodsAnnotatedWith(annotation: Class<T>): Set<Pair<T, Method>> {
    val annotatedMethods = mutableSetOf<Pair<T, Method>>()

    for (method in declaredMethods) {
        if (method.isAnnotationPresent(annotation)) {
            annotatedMethods.add(Pair(method.getAnnotation(annotation), method))
        }
    }

    return annotatedMethods
}
