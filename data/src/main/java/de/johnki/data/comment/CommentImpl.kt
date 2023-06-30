package de.johnki.data.comment

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Comment")
internal data class CommentImpl (
    @PrimaryKey
    val id: Int,
    val postId: Int,
    override val name: String,
    override val body: String,
    override val email: String
): Comment
