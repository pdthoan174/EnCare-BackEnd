package enclave.encare.encare.service.impl;

import enclave.encare.encare.form.MessageForm;
import enclave.encare.encare.model.Account;
import enclave.encare.encare.model.Channel;
import enclave.encare.encare.model.Message;
import enclave.encare.encare.modelResponse.MessageResponse;
import enclave.encare.encare.repository.MessageRepository;
import enclave.encare.encare.service.AccountService;
import enclave.encare.encare.service.ChannelService;
import enclave.encare.encare.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    @Override
    public List<MessageResponse> listMessage(long channelId, int page) {
        Pageable pageable = PageRequest.of(page, 5);
        List<Message> messageList = messageRepository.listMessageOfChannel(channelId, pageable);
        List<MessageResponse> messageResponseList = new ArrayList<MessageResponse>();
        for (Message message:messageList){
            MessageResponse messageResponse = transformData(message);
            messageResponseList.add(messageResponse);
        }
        return messageResponseList;
    }

    @Override
    public MessageResponse newMessage(MessageForm messageForm) {
        Message message = new Message();
        message.setMessage(messageForm.getMessage());
        message.setCreateDate(new Date());
        Channel channel = new Channel(messageForm.getChannelId());
        message.setChannel(channel);
        Account account = new Account(messageForm.getAccountId());
        message.setAccount(account);
        return transformData(messageRepository.save(message));
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
