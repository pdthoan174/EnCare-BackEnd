package enclave.encare.encare.check;

import enclave.encare.encare.form.MessageForm;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;

import java.util.Scanner;

public class ClientUser {
    public static void main(String[] args) throws Exception {
        long accountUserId = 1;
        long channelId = 1;

        Scanner scanner = new Scanner(System.in);
        WebSocketClient client = new StandardWebSocketClient();
        WebSocketStompClient stompClient = new WebSocketStompClient(client);
        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        ClientSessionHandler clientSessionHandler = new ClientSessionHandler();
        ListenableFuture<StompSession> listenableFuture = stompClient.connect(
                "ws://localhost:8081/ws",clientSessionHandler
        );
        StompSession session = listenableFuture.get();
        session.subscribe("/topic/messages/"+accountUserId,clientSessionHandler);
        while (true){
            Thread.sleep(2000);
            System.out.print("Said something: ");
            String text = scanner.nextLine();
            session.send("/app/chat", new MessageForm(channelId, accountUserId, text));
        }
    }
}
