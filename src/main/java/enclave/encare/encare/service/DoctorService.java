package enclave.encare.encare.service;

import enclave.encare.encare.form.RegisterFormDoctor;
import enclave.encare.encare.modelResponse.DoctorResponse;

import java.util.List;

public interface DoctorService {
    DoctorResponse findById(long id);
    boolean register(RegisterFormDoctor registerFormDoctor);
    List<DoctorResponse> listDoctorOfCategoryRating(long categoryId, int page, float rating);
    void updateRating(long appointmentId, int number);
    List<DoctorResponse> findLikeName(String name, int page);
}
