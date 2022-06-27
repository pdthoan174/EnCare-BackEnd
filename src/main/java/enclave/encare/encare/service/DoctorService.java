package enclave.encare.encare.service;

import enclave.encare.encare.form.RegisterFormDoctor;
import enclave.encare.encare.modelResponse.DoctorResponse;

import java.util.List;

public interface DoctorService {
    DoctorResponse findById(long id);
    long findDoctorIdByAccountId(long accountId);
    boolean register(RegisterFormDoctor registerFormDoctor);
    List<DoctorResponse> listDoctorOfCategoryRating(long categoryId, int page, float rating, double lon, double lat);
    void updateRating(long appointmentId, float number);
    List<DoctorResponse> findLikeName(String name, int page);
}
