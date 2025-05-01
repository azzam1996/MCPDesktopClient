import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import mcp.MCPClient
import presentation.chat.ChatMessageModel
import presentation.chat.ChatScreen
import presentation.chat.ChatState
import utils.GAMES_SERVER_URL
import utils.WEATHER_SERVER_URL
import java.awt.Dimension

@Composable
@Preview
fun App() {

    val scope = rememberCoroutineScope()
    var chatState by remember {
        mutableStateOf(ChatState())
    }
    val mcpClient by remember {
        mutableStateOf(MCPClient())
    }

    LaunchedEffect(true) {
        mcpClient.connectToServer(
            WEATHER_SERVER_URL,
            mcpClient.mcpWeather
        )
        mcpClient.connectToServer(
            GAMES_SERVER_URL,
            mcpClient.mcpGames
        )
    }
    MaterialTheme {
        ChatScreen(
            state = chatState,
            onSendMessage = { sendMessage ->
                scope.launch(Dispatchers.IO) {
                    chatState = chatState.copy(
                        messages = chatState.messages + ChatMessageModel(sendMessage, true),
                        isLoading = true
                    )

                    val response = mcpClient.processQuery(sendMessage)

                    chatState = chatState.copy(
                        messages = chatState.messages + ChatMessageModel(response, false),
                        isLoading = false
                    )
                }
            }
        )
    }
}

fun main() = application {
    val state = rememberWindowState(
        placement = WindowPlacement.Maximized,
        position = WindowPosition(0.dp, 0.dp),
    )
    Window(
        title = "MCP Client App",
        state = state,
        onCloseRequest = ::exitApplication
    ) {
        this.window.minimumSize = Dimension(1000, 600)
        App()
    }
}
