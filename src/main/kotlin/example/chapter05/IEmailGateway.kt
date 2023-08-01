package example.chapter05

interface IEmailGateway {
    fun sendGreetingsEmail(userEmail: String)
    fun sendReceipt(email: String, productName: String, quantity: Int)
}
