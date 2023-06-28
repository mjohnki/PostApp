package de.johnki.data.login

internal class UserRepository(
    private val dao: UserDao
) {
    suspend fun insert(user: UserImpl){
        dao.insert(user)
    }

    suspend fun find() : UserImpl = dao.find()
}
