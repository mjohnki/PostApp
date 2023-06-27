package de.johnki.data

import androidx.room.Database
import androidx.room.RoomDatabase
import de.johnki.data.login.UserImpl
import de.johnki.data.login.UserDao

@Database(entities = [UserImpl::class], version = 1)
internal abstract class AppDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
}
