package de.johnki.data.comment

import de.johnki.data.post.Post

internal  data class PostCommentListImpl (
    override val post: Post,
    override val comments: List<Comment>
): PostCommentList
