package example.chapter07.crm

class CRMMessageBusService {
    fun sendEmailChangedMessage(userId: Long, newEmail: String) {
        println("Email changed for user $userId to $newEmail")
    }
}
