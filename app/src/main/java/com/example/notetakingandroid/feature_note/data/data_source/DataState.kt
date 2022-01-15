package com.example.notetakingandroid.feature_note.data.data_source

data class DataState<out T>(
    val data: T? = null,
    val error: String? = null,
    val loading: Boolean = false,
) {
    companion object{
        fun <T> success(data: T?) : DataState<T>
        {
            return DataState(data = data)
        }

        fun <T> failure(message: String): DataState<T>
        {
            return DataState( error = message)
        }

        fun <T> loading(): DataState<T> = DataState(loading = true)
    }
}