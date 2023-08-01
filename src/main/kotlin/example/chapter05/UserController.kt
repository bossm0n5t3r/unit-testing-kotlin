package example.chapter05

class UserController {
    fun renameUser(userId: Int, newName: String) {
        val user = getUserFromDatabase(userId)
        user.name = newName
        saveUserToDatabase(user)
    }

    private fun getUserFromDatabase(userId: Int): User {
        println("Get user from database | userId: $userId")
        return User()
    }

    private fun saveUserToDatabase(user: User) {
        println("Save user to database | user: $user")
    }
}
