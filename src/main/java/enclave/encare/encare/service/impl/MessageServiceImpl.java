package enclave.encare.encare.service.impl;

import enclave.encare.encare.model.Message;
import enclave.encare.encare.modelResponse.MessageResponse;
import enclave.encare.encare.repository.MessageRepository;
import enclave.encare.encare.service.AccountService;
import enclave.encare.encare.service.ChannelService;
import enclave.encare.encare.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {

    @Autowired
    MessageRepository messageRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    ChannelService channelService;

    @Override
    public MessageResponse findById(long id) {
        return transformData(messageRepository.findByMessageId(id));
    }

    private MessageResponse transformData(Message message){
        MessageResponse messageResponse = new MessageResponse();

        messageResponse.setMessageId(message.getMessageId());
        messageResponse.setMessage(message.getMessage());
        messageResponse.setAccountResponse(accountService.findById(message.getAccount().getAccountId()));
        messageResponse.setChannelResponse(channelService.findById(message.getChannel().getChannelId()));

        return messageResponse;
    }
}
