package de.johnki.data.post

import retrofit2.http.GET
import retrofit2.http.Query

internal interface PostEndpoint {

    @GET(value = "posts")
    suspend fun getPosts(@Query("userId") userId: Int): List<PostImpl>

}
