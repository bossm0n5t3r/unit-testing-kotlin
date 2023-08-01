package example.chapter05

class CustomerRepository {
    fun getById(customerId: Int): Customer {
        println("Get customer by customerId: $customerId")
        return Customer()
    }
}
