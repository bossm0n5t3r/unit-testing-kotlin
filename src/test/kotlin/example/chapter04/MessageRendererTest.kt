package example.chapter04

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class MessageRendererTest {
    @Disabled(
        """
            SUT 가 생성한 결과가 아니라 SUT 의 구현 세부 사항과 결합해서 깨지기 쉽다.
            따라서 리팩터링 내성이 없다.
        """,
    )
    @Test
    fun messageRenderer_uses_correct_sub_renderers() {
        val sut = MessageRenderer()

        val renderers = sut.subRenderers

        assertThat(renderers).hasSize(3)
        assertThat(renderers[0]).isInstanceOf(HeaderRenderer::class.java)
        assertThat(renderers[1]).isInstanceOf(BodyRenderer::class.java)
        assertThat(renderers[2]).isInstanceOf(FooterRenderer::class.java)
    }

    @Test
    fun rendering_a_message() {
        val sut = MessageRenderer()
        val message = Message("h", "b", "f")

        val html = sut.render(message)

        assertEquals("<h1>h</h1><b>b</b><i>f</i>", html)
    }
}
