package enclave.encare.encare.service;

import enclave.encare.encare.modelResponse.HospitalResponse;

import java.util.List;

public interface HospitalService {
    HospitalResponse findById(long id);
    List<HospitalResponse> findAll();
}
