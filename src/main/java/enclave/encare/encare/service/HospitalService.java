package enclave.encare.encare.service;

import enclave.encare.encare.modelResponse.HospitalResponse;

public interface HospitalService {
    HospitalResponse findById(long id);
}
