package example.chapter06

class PriceEngine {
    fun calculateDiscount(vararg products: Product): Double {
        return (products.size * 0.01).coerceAtMost(0.2)
    }
}
