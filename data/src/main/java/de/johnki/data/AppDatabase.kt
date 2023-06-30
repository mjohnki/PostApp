package de.johnki.data

import androidx.room.Database
import androidx.room.RoomDatabase
import de.johnki.data.comment.CommentDao
import de.johnki.data.comment.CommentImpl
import de.johnki.data.login.UserImpl
import de.johnki.data.login.UserDao
import de.johnki.data.post.PostDao
import de.johnki.data.post.PostImpl

@Database(entities = [UserImpl::class, PostImpl::class, CommentImpl::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun postDao(): PostDao

    abstract fun commentDao(): CommentDao
}
