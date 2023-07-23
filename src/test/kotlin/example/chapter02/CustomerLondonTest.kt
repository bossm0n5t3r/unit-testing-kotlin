package example.chapter02

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

class CustomerLondonTest {
    private val mockStore = mockk<Store>(relaxed = true)

    @Test
    fun purchase_succeeds_when_enough_inventory() {
        every { mockStore.hasEnoughInventory(any(), any()) } returns true
        val customer = Customer()

        val success = customer.purchase(mockStore, Product.SHAMPOO, 5)

        Assertions.assertTrue(success)
        verify(exactly = 1) {
            mockStore.removeInventory(Product.SHAMPOO, 5)
        }
    }

    @Test
    fun purchase_fails_when_not_enough_inventory() {
        every { mockStore.hasEnoughInventory(any(), any()) } returns false
        val customer = Customer()

        val success = customer.purchase(mockStore, Product.BOOK, 15)

        Assertions.assertFalse(success)
        verify(exactly = 0) {
            mockStore.removeInventory(Product.SHAMPOO, 5)
        }
    }
}
