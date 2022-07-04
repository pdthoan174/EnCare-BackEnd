package enclave.encare.encare.service;

import enclave.encare.encare.form.DoctorInformationForm;
import enclave.encare.encare.form.RegisterFormDoctor;
import enclave.encare.encare.model.Doctor;
import enclave.encare.encare.model.User;
import enclave.encare.encare.modelResponse.DoctorResponse;
import org.springframework.data.jpa.repository.Query;

import java.text.ParseException;
import java.util.List;

public interface DoctorService {
    DoctorResponse findById(long id);
    DoctorResponse findByName(String name);
    String updateInfor(DoctorInformationForm doctorInformationForm) throws ParseException;
    boolean register(RegisterFormDoctor registerFormDoctor);
    boolean changeStatusAppointment(long appointmentId,int statusId);
    List<DoctorResponse> listDoctor();
    List<DoctorResponse> listDoctor(int page);
    List<DoctorResponse> listDoctorOfHospital(long hospitalId);
    List<DoctorResponse> listDoctorOfHospital(long hospitalId,int page);
    List<DoctorResponse> listDoctorOfCategory(long categoryId);
    List<DoctorResponse> listDoctorOfCategory(long categoryId, int page);
}
