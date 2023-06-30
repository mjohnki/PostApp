package de.johnki.data.comment

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import de.johnki.data.post.PostImpl
import kotlinx.coroutines.flow.Flow

@Dao
internal interface CommentDao {

    @Query("SELECT * FROM Comment where postId = :postId order by id")
    fun findByPostId(postId: Int): Flow<List<CommentImpl>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(comments: List<CommentImpl>)
}
