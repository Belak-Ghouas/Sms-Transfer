package com.belak.pipe.utils


sealed class Result<out T> {


    data class Success<out T>(val value: T): Result<T>()
    data class Failure<T>(
        val errorCode:Int?,
        val message: String?,
    ): Result<T>()



    class Cancelled<T> : Result<T>()
    class Unknown<T> : Result<T>()
    class Waiting<T>: Result<T>()


    inline fun <M> transform(process: (T) -> M): Result<M> = when (this) {
        is Success -> Success(process(this.value))
        is Failure -> Failure(errorCode,message)
        is Cancelled -> Cancelled()
        is Unknown -> Unknown()
        is Waiting ->Waiting()
    }

}

inline fun <reified T> Result<T>.doIfFailure(callback: (errorCode:Int, error: String?) -> Unit) {
    if (this is Result.Failure) {
        if (errorCode != null) {
            callback(errorCode,message)
        }
    }
}

inline fun <reified T> Result<T>.doIfSuccess(callback: (value: T) -> Unit) {
    when(val t =this){
        is Result.Success -> callback.invoke(t.value)
        else -> {}
    }
}

inline fun <reified T> Result<T>.doIfWaiting(callback: () -> Unit) {
    if (this is Result.Waiting) {
        callback()
    }
}