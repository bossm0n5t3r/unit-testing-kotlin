package example.chapter05

class EmailController {
    private lateinit var emailGateway: IEmailGateway
    private lateinit var database: IDatabase

    constructor(emailGateway: IEmailGateway) {
        this.emailGateway = emailGateway
    }

    constructor(database: IDatabase) {
        this.database = database
    }

    fun greetUser(userEmail: String) {
        emailGateway.sendGreetingsEmail(userEmail)
    }

    fun createReport(): Report {
        val numberOfUsers = database.getNumberOfUsers()
        return Report(numberOfUsers)
    }
}
