package com.zfml.simplenote.domain.util

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Date

object Constants {
    const val WRITE_NOTE_KEY = "note_key"
}

@RequiresApi(Build.VERSION_CODES.O)
fun Long.toReadableDate(): String=
    DateTimeFormatter
        .ofPattern("dd MMM yyyy")
        .format(this.toLocalDateTime())



@RequiresApi(Build.VERSION_CODES.O)
fun Long.toLocalDateTime(): LocalDateTime {
    return Date(this).toInstant()
        .atZone(ZoneId.systemDefault())
        .toLocalDateTime()
}