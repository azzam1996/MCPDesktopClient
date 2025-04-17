package presentation

data class ChatState(
    val messages: List<ChatMessageModel> = emptyList()
)
