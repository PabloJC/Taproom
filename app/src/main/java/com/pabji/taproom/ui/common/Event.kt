package com.pabji.taproom.ui.common

/**
 * Used as a wrapper for data that is exposed via a LiveData that represents an event.
 */
data class Event<out T>(private val content: T) {

    private var hasBeenHandled = false

    /**
     * Returns the content and prevents its use again.
     */
    fun getContent(): T? {
        return if (hasBeenHandled) {
            null
        } else {
            hasBeenHandled = true
            content
        }
    }
}