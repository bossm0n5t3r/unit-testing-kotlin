package example.chapter02

import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CustomerClassicalTest {
    @Test
    fun purchase_succeeds_when_enough_inventory() {
        val store = Store()
        store.addInventory(Product.SHAMPOO, 10)
        val customer = Customer()

        val success = customer.purchase(store, Product.SHAMPOO, 5)

        assertTrue(success)
        assertEquals(
            5,
            store.getInventory(Product.SHAMPOO),
        )
    }

    @Test
    fun purchase_fails_when_not_enough_inventory() {
        val store = Store()
        store.addInventory(Product.BOOK, 10)
        val customer = Customer()

        val success = customer.purchase(store, Product.BOOK, 15)

        assertFalse(success)
        assertEquals(
            10,
            store.getInventory(Product.BOOK),
        )
    }
}
