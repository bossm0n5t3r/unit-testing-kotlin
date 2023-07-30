package example.chapter05

import example.chapter02.Customer
import example.chapter02.Product
import example.chapter02.Store
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class CustomerTest {
    @Test
    fun purchase_succeeds_when_enough_inventory() {
        val storeMock = mockk<Store>(relaxed = true) {
            // 준비된 응답을 설정
            every { hasEnoughInventory(Product.SHAMPOO, 5) } returns true
        }
        val sut = Customer()

        val success = sut.purchase(storeMock, Product.SHAMPOO, 5)

        assertTrue(success)
        // SUT 에서 수행한 호출을 검사
        verify(exactly = 1) {
            storeMock.removeInventory(Product.SHAMPOO, 5)
        }
    }
}
