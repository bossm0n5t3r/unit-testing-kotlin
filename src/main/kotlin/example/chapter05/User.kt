package example.chapter05

class User {
    var name: String
        get() = ""
        set(value) {
            normalizedName(value)
        }

    private fun normalizedName(name: String): String {
        return name.trim().take(50)
    }
}
