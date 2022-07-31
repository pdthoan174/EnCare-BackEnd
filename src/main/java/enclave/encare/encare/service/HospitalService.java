package enclave.encare.encare.service;

import enclave.encare.encare.model.Hospital;
import enclave.encare.encare.modelResponse.HospitalResponse;

import java.util.List;

public interface HospitalService {
    HospitalResponse findById(long id);
    List<HospitalResponse> findAll();
    void saveAll(List<Hospital> hospitalList);
}
