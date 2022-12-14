package enclave.encare.encare.service.impl;

import enclave.encare.encare.model.Hospital;
import enclave.encare.encare.modelResponse.HospitalResponse;
import enclave.encare.encare.repository.HospitalRepository;
import enclave.encare.encare.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HospitalServiceImpl implements HospitalService {

    @Autowired
    HospitalRepository hospitalRepository;

    @Override
    public HospitalResponse findById(long id) {
        try {
            Hospital hospital = hospitalRepository.findByHospitalId(id);
            if (hospital!=null){
                return transformData(hospital);
            }
            return null;
        }catch (Exception e){
            return null;
        }
    }

    @Override
    public List<HospitalResponse> findAll() {
        List<HospitalResponse> hospitalResponses = new ArrayList<>();
        hospitalResponses = transformData(hospitalRepository.findAll());
        return hospitalResponses;
    }

    @Override
    public void saveAll(List<Hospital> hospitalList) {
        hospitalRepository.saveAll(hospitalList);
    }

    private HospitalResponse transformData(Hospital hospital){
        HospitalResponse hospitalResponse = new HospitalResponse();

        hospitalResponse.setHospitalId(hospital.getHospitalId());
        hospitalResponse.setDescription(hospital.getDescription());
        hospitalResponse.setLatMap(hospital.getLatMap());
        hospitalResponse.setLongMap(hospital.getLongMap());
        hospitalResponse.setRating(hospital.getRating());
        hospitalResponse.setCountRating(hospital.getCountRating());
        hospitalResponse.setAddress(hospital.getAddress());
        hospitalResponse.setName(hospital.getName());

        return hospitalResponse;
    }
    private List<HospitalResponse> transformData(List<Hospital> hospitalList){
        List<HospitalResponse> hospitalResponses = new ArrayList<>();
        for (Hospital hospital: hospitalList) {
            hospitalResponses.add(transformData(hospital));
        }
        return hospitalResponses;
    }
}
