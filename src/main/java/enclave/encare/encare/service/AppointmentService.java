package enclave.encare.encare.service;

import enclave.encare.encare.form.AppointmentForm;
import enclave.encare.encare.form.FreeTimeForm;
import enclave.encare.encare.model.Doctor;
import enclave.encare.encare.modelResponse.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse findById(long id);
    boolean newAppointment(AppointmentForm appointmentForm);
    List<AppointmentResponse> historyAppointmentUser(long userId);
    List<Integer> listFreeTime(FreeTimeForm freeTimeForm);
}
