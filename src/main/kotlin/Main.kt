import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.launch
import presentation.ChatMessageModel
import presentation.ChatScreen
import presentation.ChatState

@Composable
@Preview
fun App() {
    var text by remember { mutableStateOf("Hello, World!") }

    val  scope = rememberCoroutineScope()
    var chatState by remember {
        mutableStateOf(ChatState())
    }
    MaterialTheme {


        ChatScreen(
            state = chatState,
            onDisconnect = {},
            onSendMessage = { sendMessage ->
                scope.launch {
                    chatState = chatState.copy(messages =  chatState.messages + ChatMessageModel(sendMessage,true))
                    val mcpClient = MCPClient()
                    mcpClient.connectToServer("E:/intellijProjects/NewServer/weather-stdio-server/build/libs/weather-stdio-server-0.1.0-all.jar")
                    val response = mcpClient.processQuery(sendMessage)
                    chatState = chatState.copy(messages =  chatState.messages + ChatMessageModel(response,false))
                }
            }
        )
//        Button(onClick = {
//            scope.launch {
//                val mcpClient = MCPClient()
//                mcpClient.connectToServer("E:/intellijProjects/NewServer/weather-stdio-server/build/libs/weather-stdio-server-0.1.0-all.jar")
//                println(mcpClient.processQuery("alerts in TX"))
//            }
//
//        }) {
//            Text(text)
//        }
    }
}

fun main() = application {
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
