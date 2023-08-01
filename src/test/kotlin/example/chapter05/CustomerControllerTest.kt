package example.chapter05

import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class CustomerControllerTest {
    /**
     * 취약한 테스트로 이어지지 않는 목 사용
     */
    @Test
    fun successful_purchase() {
        val mock = mockk<IEmailGateway>(relaxed = true)
        val mainStore = Store().also {
            it.addInventory(Product(2), 5)
        }
        val sut = CustomerController(mock, mainStore)

        val isSuccess = sut.purchase(1, 2, 5)

        assertTrue(isSuccess)
        verify(exactly = 1) {
            mock.sendReceipt(any(), any(), 5)
        }
    }
}
