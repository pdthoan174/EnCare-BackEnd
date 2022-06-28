package enclave.encare.encare.service.impl;

import enclave.encare.encare.model.Account;
import enclave.encare.encare.model.Channel;
import enclave.encare.encare.model.Doctor;
import enclave.encare.encare.model.User;
import enclave.encare.encare.modelResponse.ChannelResponse;
import enclave.encare.encare.repository.ChannelRepository;
import enclave.encare.encare.service.AccountService;
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
    AccountService accountService;

    @Override
    public ChannelResponse findById(long id) {
        return transformData(channelRepository.findByChannelId(id));
    }

    @Override
    public ChannelResponse loadChannel(long userId, long doctorId) {
        Account doctor = new Account(doctorId);
        Account user = new Account(userId);
        Channel channel = channelRepository.findByDoctorAndUser(doctor, user);
        if (channel!=null){
            return transformData(channel);
        }
        channel.setDoctor(doctor);
        channel.setUser(user);
        return transformData(channelRepository.save(channel));
    }


    private ChannelResponse transformData(Channel channel){
        ChannelResponse channelResponse = new ChannelResponse();

        channelResponse.setChannelId(channel.getChannelId());
        channelResponse.setDoctorResponse(accountService.findById(channel.getDoctor().getAccountId()));
        channelResponse.setUserResponse(accountService.findById(channel.getUser().getAccountId()));

        return channelResponse;
    }
}
