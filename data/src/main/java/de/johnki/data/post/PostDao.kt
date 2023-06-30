package de.johnki.data.post

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
internal interface PostDao {

    @Query("SELECT * FROM Post order by title")
    fun findAll(): Flow<List<PostImpl>>

    @Query("SELECT * FROM Post where id = :postId")
    suspend fun find(postId: Int): PostImpl

    @Query("SELECT * FROM Post where id = :postId")
    fun findPostFlow(postId: Int): Flow<PostImpl>

    @Query("SELECT * FROM Post where fav is 1 order by title")
    fun findFav(): Flow<List<PostImpl>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(posts: List<PostImpl>)

    @Update
    suspend fun update(post: PostImpl)


}
