package example.chapter02

class Customer {
    fun purchase(store: Store, product: Product, quantity: Int): Boolean {
        return if (store.hasEnoughInventory(product, quantity).not()) {
            false
        } else {
            store.removeInventory(product, quantity)
            true
        }
    }
}
