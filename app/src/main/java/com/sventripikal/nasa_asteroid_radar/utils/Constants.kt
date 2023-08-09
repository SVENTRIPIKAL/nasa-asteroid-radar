package com.sventripikal.nasa_asteroid_radar.utils

import timber.log.Timber


/**
 *  LOGGING
 */
// logging tag
const val TAG = "_SVENTRIPIKAL"

// logging message
const val MESSAGE_CREATE = "[ON-CREATE]"
const val MESSAGE_START = "[ON-START]"
const val MESSAGE_RESUME = "[ON-RESUME]"
const val MESSAGE_PAUSE = "[ON-PAUSE]"
const val MESSAGE_STOP = "[ON-STOP]"
const val MESSAGE_DESTROY = "[ON-DESTROY]"

// logging priority enum
enum class Priority { ERROR, VERBOSE, DEBUG, INFO }

// timber logging function
fun timber(tag: String, message: String, priority: Priority) {

    when (priority) {
        Priority.ERROR -> Timber.tag(tag).e(message)
        Priority.VERBOSE -> Timber.tag(tag).v(message)
        Priority.DEBUG -> Timber.tag(tag).d(message)
        Priority.INFO -> Timber.tag(tag).i(message)
    }
}