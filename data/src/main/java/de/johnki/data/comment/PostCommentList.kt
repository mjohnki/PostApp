package de.johnki.data.comment

import de.johnki.data.post.Post

interface PostCommentList {
    val post: Post
    val comments: List<Comment>
}
