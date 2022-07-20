package enclave.encare.encare.service;

import enclave.encare.encare.model.Status;
import enclave.encare.encare.modelResponse.StatusResponse;

import java.util.List;

public interface StatusService {
    StatusResponse findById(long id);
    List<StatusResponse> findAll();
}
