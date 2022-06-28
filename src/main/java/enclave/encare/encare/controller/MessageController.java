package enclave.encare.encare.controller;

import enclave.encare.encare.form.MessageForm;
import enclave.encare.encare.jwt.JwtTokenProvider;
import enclave.encare.encare.model.ResponseObject;
import enclave.encare.encare.modelResponse.ChannelResponse;
import enclave.encare.encare.modelResponse.MessageResponse;
import enclave.encare.encare.service.ChannelService;
import enclave.encare.encare.service.MessageService;
import enclave.encare.encare.until.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class MessageController {

    @Autowired
    SimpMessagingTemplate template;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

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

    @GetMapping("/chat/accountDoctorId={accountDoctorId}")
    public ResponseEntity<ResponseObject> chatWithDoctor(@PathVariable("accountDoctorId") long accountDoctorId){
        long accountUserId = getAccountId();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Information channel", channelService.findChannelId(accountUserId, accountDoctorId))
        );
    }

    @GetMapping("/chat/accountUserId={accountUserId}")
    public ResponseEntity<ResponseObject> chatWithUser(@PathVariable("accountUserId") long accountUserId){
        long accountDoctorId = getAccountId();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"Information channel", channelService.findChannelId(accountUserId, accountDoctorId))
        );
    }

    @GetMapping("/listMessage")
    public ResponseEntity<ResponseObject> listMessage(
            @RequestParam(required = true, name = "channelId") long channelId,
            @RequestParam(required = false, name = "page", defaultValue = "0") int page){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "List message", messageService.listMessage(channelId, page))
        );
    }

    private long getAccountId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null){
            String token = jwtTokenProvider.generateToken((CustomUserDetail) authentication.getPrincipal());
            return jwtTokenProvider.getUserId(token);
        }
        return 0;
    }
}
