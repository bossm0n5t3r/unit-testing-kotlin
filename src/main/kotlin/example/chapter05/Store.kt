package example.chapter05

class Store : IStore {
    private val inventory = mutableMapOf<Product, Int>()

    override fun hasEnoughInventory(product: Product, quantity: Int): Boolean {
        return getInventory(product) >= quantity
    }

    override fun removeInventory(product: Product, quantity: Int) {
        if (hasEnoughInventory(product, quantity).not()) {
            error("Not enough inventory")
        }

        inventory[product] = inventory.getOrDefault(product, 0) - quantity
    }

    override fun addInventory(product: Product, quantity: Int) {
        inventory[product] = inventory.getOrDefault(product, 0) + quantity
    }

    override fun getInventory(product: Product): Int {
        return inventory[product] ?: 0
    }
}
