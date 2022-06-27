package enclave.encare.encare.service;

import enclave.encare.encare.form.AppointmentForm;
import enclave.encare.encare.modelResponse.AppointmentResponse;

public interface AppointmentService {
    AppointmentResponse findById(long id);
    boolean newAppointment(AppointmentForm appointmentForm);
}
