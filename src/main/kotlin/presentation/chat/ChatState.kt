package presentation.chat

data class ChatState(
    val messages: List<ChatMessageModel> = emptyList(),
    val isLoading: Boolean = false
)
