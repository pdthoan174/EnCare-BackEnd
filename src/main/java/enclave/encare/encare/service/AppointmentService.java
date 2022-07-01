package enclave.encare.encare.service;

import enclave.encare.encare.form.AppointmentForm;
import enclave.encare.encare.modelResponse.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse findById(long id);
    boolean newAppointment(AppointmentForm appointmentForm);
    boolean setDescription(long id,String description);
    List<AppointmentResponse> findByDoctorId(long doctorId);
}
