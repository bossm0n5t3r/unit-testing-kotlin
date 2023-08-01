package example.chapter05

import java.util.UUID

class Customer {
    val email = UUID.randomUUID().toString()

    fun purchase(store: IStore, product: Product, quantity: Int): Boolean {
        return if (store.hasEnoughInventory(product, quantity).not()) {
            false
        } else {
            store.removeInventory(product, quantity)
            true
        }
    }
}
