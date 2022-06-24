package enclave.encare.encare.service;

import enclave.encare.encare.form.RegisterFormDoctor;
import enclave.encare.encare.model.Doctor;
import enclave.encare.encare.modelResponse.DoctorResponse;

import javax.print.Doc;
import java.util.List;

public interface DoctorService {
    DoctorResponse findById(long id);
    boolean register(RegisterFormDoctor registerFormDoctor);
    List<DoctorResponse> listDoctorOfCategory(long categoryId);
}
