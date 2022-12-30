package com.sms.pipe.utils


inline fun <C, R> C.ifNotEmpty(defaultValue: (C) -> Unit) where  C : R, R : CharSequence {
    if (!isEmpty()) defaultValue(this)
}

val Any.classTag: String get() { return this::class.java.simpleName }
