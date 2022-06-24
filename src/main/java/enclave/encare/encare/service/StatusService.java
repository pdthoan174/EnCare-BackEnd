package enclave.encare.encare.service;

import enclave.encare.encare.modelResponse.StatusResponse;

public interface StatusService {
    StatusResponse findById(long id);
}
