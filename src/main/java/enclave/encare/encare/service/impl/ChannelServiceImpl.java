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
        try {
            Channel channel = channelRepository.findByChannelId(id);
            if (channel!=null){
                return transformData(channel);
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public ChannelResponse findChannelId(long accountUserId, long accountDoctorId) {
        Channel channel = channelRepository.findChannel(accountDoctorId, accountUserId);
        if (channel == null){
            Account doctor = new Account(accountDoctorId);
            Account user = new Account(accountUserId);
            Channel c = new Channel();
            c.setDoctor(doctor);
            c.setUser(user);
            return transformData(channelRepository.save(c));
        }

        return transformData(channel);
    }

    private ChannelResponse transformData(Channel channel){
        ChannelResponse channelResponse = new ChannelResponse();

        channelResponse.setChannelId(channel.getChannelId());
        channelResponse.setDoctorResponse(accountService.findById(channel.getDoctor().getAccountId()));
        channelResponse.setUserResponse(accountService.findById(channel.getUser().getAccountId()));

        return channelResponse;
    }
}
