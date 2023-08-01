package example.chapter05

class ProductRepository {
    fun getById(productId: Int): Product {
        println("Get product by productId: $productId")
        return Product(productId)
    }
}
