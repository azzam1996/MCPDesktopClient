package presentation.chat

data class ChatMessageModel(
    val text: String,
    val isFromLocalUser: Boolean = false
)
