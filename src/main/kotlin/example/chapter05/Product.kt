package example.chapter05

import java.util.UUID

data class Product(
    val id: Int,
) {
    val name: String = UUID.randomUUID().toString()
}
