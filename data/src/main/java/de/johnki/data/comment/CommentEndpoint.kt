package de.johnki.data.comment

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

internal interface CommentEndpoint {

    @GET(value = "posts/{postId}/comments")
    suspend fun getCommentsForPostId(@Path("postId") postId: Int): List<CommentImpl>

}
