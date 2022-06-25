package enclave.encare.encare.service;

import enclave.encare.encare.form.AppointmentForm;
import enclave.encare.encare.form.FreeTimeForm;
import enclave.encare.encare.modelResponse.AppointmentResponse;

import java.util.List;

public interface AppointmentService {
    AppointmentResponse findById(long id);
    boolean newAppointment(AppointmentForm appointmentForm);
    List<AppointmentResponse> historyAppointmentUser(long userId, int page);
    List<Integer> listFreeTime(FreeTimeForm freeTimeForm);
    boolean cancelAppointment(long userId, long appointmentId);
}
