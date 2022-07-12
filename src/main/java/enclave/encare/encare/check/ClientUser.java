package enclave.encare.encare.check;

import enclave.encare.encare.form.MessageForm;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.socket.client.WebSocketClient;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.RestTemplateXhrTransport;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientUser {
    public static void main(String[] args) throws Exception {
        long accountUserId = 44;
        long channelId = 1;

        Scanner scanner = new Scanner(System.in);
//        WebSocketClient client = new StandardWebSocketClient();
//        WebSocketStompClient stompClient = new WebSocketStompClient(client);

        List<Transport> transports = new ArrayList<>(2);
        transports.add(new WebSocketTransport(new StandardWebSocketClient()));
        transports.add(new RestTemplateXhrTransport());

        SockJsClient sockjsClient = new SockJsClient(transports);
        WebSocketStompClient stompClient = new WebSocketStompClient(sockjsClient);

        stompClient.setMessageConverter(new MappingJackson2MessageConverter());

        ClientSessionHandler clientSessionHandler = new ClientSessionHandler();
        ListenableFuture<StompSession> listenableFuture = stompClient.connect(
//                "ws://enclave-encare.herokuapp.com/ws",clientSessionHandler
                "ws://13.215.200.248/ws",clientSessionHandler
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
