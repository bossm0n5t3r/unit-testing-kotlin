package example.chapter06

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class OrderTest {
    @Test
    fun adding_a_product_to_an_order() {
        val product = Product("Hand wash")
        val sut = Order()

        sut.addProduct(product)

        assertEquals(1, sut.productList.count())
        assertEquals(product, sut.productList[0])
    }
}
