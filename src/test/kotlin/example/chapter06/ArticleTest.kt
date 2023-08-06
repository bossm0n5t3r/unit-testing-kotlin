package example.chapter06

import example.chapter06.ArticleExtensions.shouldContainNumberOfComments
import example.chapter06.ArticleExtensions.withComment
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import kotlin.test.assertEquals

class ArticleTest {
    /**
     * 많은 공간을 차지하는 상태 검증
     */
    @Test
    fun adding_a_comment_to_an_article() {
        val sut = Article()
        val text = "Comment text"
        val author = "John Doe"
        val now = LocalDateTime.of(2019, 4, 1, 0, 0)

        sut.addComment(text, author, now)

        assertEquals(1, sut.commentList.count())
        assertEquals(text, sut.commentList[0].text)
        assertEquals(author, sut.commentList[0].author)
        assertEquals(now, sut.commentList[0].dateCreated)
    }

    /**
     * 검증문에 헬퍼 메서드 사용
     */
    @Test
    fun adding_a_comment_to_an_article_with_helper_method() {
        val sut = Article()
        val text = "Comment text"
        val author = "John Doe"
        val now = LocalDateTime.of(2019, 4, 1, 0, 0)

        sut.addComment(text, author, now)

        sut.shouldContainNumberOfComments(1)
            .withComment(text, author, now)
    }
}
