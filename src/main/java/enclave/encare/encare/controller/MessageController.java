package enclave.encare.encare.controller;

import enclave.encare.encare.form.MessageForm;
import enclave.encare.encare.modelResponse.ChannelResponse;
import enclave.encare.encare.modelResponse.MessageResponse;
import enclave.encare.encare.service.ChannelService;
import enclave.encare.encare.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MessageController {

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    MessageService messageService;

    @Autowired
    ChannelService channelService;

    @MessageMapping("/chat")
    public void sendMessage(MessageForm messageForm){
        MessageResponse messageResponse = messageService.newMessage(messageForm);
        ChannelResponse channelResponse = channelService.findById(messageForm.getChannelId());
        long accountDoctorId = channelResponse.getDoctorResponse().getAccountId();
        long accountUserId = channelResponse.getUserResponse().getAccountId();

        template.convertAndSend("/topic/messages/"+accountDoctorId, messageResponse);
        template.convertAndSend("/topic/messages/"+accountUserId, messageResponse);
    }
}
