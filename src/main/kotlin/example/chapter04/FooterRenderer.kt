package example.chapter04

class FooterRenderer : IRenderer {
    override fun render(message: Message): String {
        return "<i>${message.footer}</i>"
    }
}
