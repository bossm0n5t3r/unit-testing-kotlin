package example.chapter06

class Order {
    val productList = mutableListOf<Product>()

    fun addProduct(product: Product) {
        productList.add(product)
    }
}
