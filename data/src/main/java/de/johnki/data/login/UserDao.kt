package de.johnki.data.login

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
internal interface UserDao {

    @Query("SELECT * FROM User")
    suspend fun find(): UserImpl

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(user: UserImpl)
}
