package enclave.encare.encare.service;

import enclave.encare.encare.modelResponse.MessageResponse;

public interface MessageService {
    MessageResponse findById(long id);
}
