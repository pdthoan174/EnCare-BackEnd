package enclave.encare.encare.service;

import enclave.encare.encare.form.MessageForm;
import enclave.encare.encare.modelResponse.MessageResponse;

import java.util.List;

public interface MessageService {
    MessageResponse findById(long id);
    List<MessageResponse> listMessage(long channelId, int page);
    MessageResponse newMessage(MessageForm messageForm);
}
