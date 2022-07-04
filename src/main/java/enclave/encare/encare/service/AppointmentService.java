package enclave.encare.encare.service;

import enclave.encare.encare.form.AppointmentForm;
import enclave.encare.encare.modelResponse.AppointmentResponse;

import java.util.Date;
import java.util.List;

public interface AppointmentService {
    List<AppointmentResponse> findAll();
    AppointmentResponse findById(long id);
    List<AppointmentResponse> findByPhone(String phone);
    boolean newAppointment(AppointmentForm appointmentForm);
    boolean setDescription(long id,String description);
    boolean isExistTime(int time, Date date,long appointmentId);
    boolean isExistTime(long appointmentId);
    List<AppointmentResponse> findByDoctorId(long doctorId);
    List<AppointmentResponse> findByHospitalId(long hospitalId);
    List<AppointmentResponse> findByStatusId(long statusId);
}
