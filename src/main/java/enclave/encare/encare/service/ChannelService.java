package enclave.encare.encare.service;

import enclave.encare.encare.modelResponse.ChannelResponse;

public interface ChannelService {
    ChannelResponse findById(long id);
    ChannelResponse findChannelId(long accountUserId, long accountDoctorId);
}
