package example.chapter04

class MessageRenderer : IRenderer {
    val subRenderers = listOf(
        HeaderRenderer(),
        BodyRenderer(),
        FooterRenderer(),
    )

    override fun render(message: Message): String {
        return subRenderers.joinToString("") { x -> x.render(message) }
    }
}
