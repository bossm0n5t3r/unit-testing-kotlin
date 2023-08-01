package example.chapter05

class CustomerController(
    private val emailGateway: IEmailGateway,
    private val mainStore: IStore,
) {
    private val customerRepository = CustomerRepository()
    private val productRepository = ProductRepository()

    fun purchase(customerId: Int, productId: Int, quantity: Int): Boolean {
        val customer = customerRepository.getById(customerId)
        val product = productRepository.getById(productId)

        return customer.purchase(mainStore, product, quantity).also {
            if (it) {
                emailGateway.sendReceipt(customer.email, product.name, quantity)
            }
        }
    }
}
