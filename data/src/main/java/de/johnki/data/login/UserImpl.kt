package de.johnki.data.login

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
internal data class UserImpl(

    @PrimaryKey
    val id: Int,
)
