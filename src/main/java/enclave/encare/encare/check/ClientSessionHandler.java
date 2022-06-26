package enclave.encare.encare.check;

import enclave.encare.encare.modelResponse.MessageResponse;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandler;

import java.lang.reflect.Type;

public class ClientSessionHandler implements StompSessionHandler {
    @Override
    public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
        System.out.println("connect "+session.getSessionId());
    }

    @Override
    public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {

    }

    @Override
    public void handleTransportError(StompSession session, Throwable exception) {

    }

    @Override
    public Type getPayloadType(StompHeaders headers) {
        return MessageResponse.class;
    }

    @Override
    public void handleFrame(StompHeaders headers, Object payload) {
        MessageResponse messageResponse = (MessageResponse) payload;
        System.out.println("channel: "+messageResponse.getChannelResponse().getChannelId());
        System.out.println("account: "+messageResponse.getAccountResponse().getAccountId()+", "+messageResponse.getAccountResponse().getName());
        System.out.println("message: "+messageResponse.getMessage());
    }
}
