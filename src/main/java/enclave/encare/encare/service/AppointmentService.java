package enclave.encare.encare.service;

import enclave.encare.encare.form.AppointmentForm;
//<<<<<<< HEAD
import enclave.encare.encare.form.FreeTimeForm;
import enclave.encare.encare.modelResponse.AppointmentResponse;

//=======
import java.util.Date;
//>>>>>>> doctor
import java.util.List;

public interface AppointmentService {

    AppointmentResponse findById(long id);
//<<<<<<< HEAD
    boolean newAppointment(AppointmentForm appointmentForm);
    List<AppointmentResponse> historyAppointmentUser(long userId, int page);
    List<Integer> listFreeTime(FreeTimeForm freeTimeForm);
    boolean cancelAppointment(long accountId, long appointmentId);
    List<AppointmentResponse> findAll();
//=======

    List<AppointmentResponse> findByPhone(String phone);

    List<AppointmentResponse> findByStatus(Long statusId);

    String setDescription(long id, String description);

    boolean isExistTime(int time, Date date, long appointmentId);

    boolean isExistTime(long appointmentId);

    List<AppointmentResponse> findByDoctorId(long doctorId);

    List<AppointmentResponse> findByHospitalId(long hospitalId);

    List<AppointmentResponse> findByStatusId(long statusId);
//>>>>>>> doctor
}
