package example.chapter06

import java.time.LocalDateTime

class Article {
    val commentList = mutableListOf<Comment>()

    fun addComment(text: String, author: String, now: LocalDateTime) {
        commentList.add(Comment(text, author, now))
    }
}
