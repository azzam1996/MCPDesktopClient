package presentation

data class ChatMessageModel(
    val text: String,
    val isFromLocalUser: Boolean = false
)
