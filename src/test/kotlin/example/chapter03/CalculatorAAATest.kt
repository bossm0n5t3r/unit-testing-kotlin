package example.chapter03

import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class CalculatorAAATest {
    @Test
    fun sum_of_two_numbers() {
        // given
        val first = 10.0
        val second = 20.0
        val calculator = Calculator()

        // when
        val result = calculator.sum(first, second)

        // then
        assertEquals(result, 30.0)
    }
}
