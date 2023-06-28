package de.johnki.data.post

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Post")
internal data class PostImpl (
    @PrimaryKey
    override val id: Int,
    val userId: Int,
    override val title: String,
    override val body: String,
    override val fav: Boolean = false
): Post
