package enclave.encare.encare.service.impl;

import enclave.encare.encare.model.Channel;
import enclave.encare.encare.modelResponse.ChannelResponse;
import enclave.encare.encare.repository.ChannelRepository;
import enclave.encare.encare.service.ChannelService;
import enclave.encare.encare.service.DoctorService;
import enclave.encare.encare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChannelServiceImpl implements ChannelService {

    @Autowired
    ChannelRepository channelRepository;

    @Autowired
    DoctorService doctorService;

    @Autowired
    UserService userService;


    @Override
    public ChannelResponse findById(long id) {
        return transformData(channelRepository.findByChannelId(id));
    }


    private ChannelResponse transformData(Channel channel){
        ChannelResponse channelResponse = new ChannelResponse();

        channelResponse.setChannelId(channel.getChannelId());
        channelResponse.setDoctorResponse(doctorService.findById(channel.getDoctor().getDoctorId()));
        channelResponse.setUserResponse(userService.findById(channel.getUser().getUserId()));

        return channelResponse;
    }
}
