package com.zfml.simplenote.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Note(
    @PrimaryKey(autoGenerate = true)
    val id: Int?= null,
    val title: String = "",
    val description: String = "",
    val date: Long = System.currentTimeMillis()
)
