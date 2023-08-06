package example.chapter06

import java.time.LocalDateTime

object ArticleExtensions {
    fun Article.shouldContainNumberOfComments(commentCount: Int): Article {
        assert(this.commentList.size == commentCount)
        return this
    }

    fun Article.withComment(text: String, author: String, dateCreated: LocalDateTime): Article {
        assert(this.commentList.contains(Comment(text, author, dateCreated)))
        return this
    }
}
