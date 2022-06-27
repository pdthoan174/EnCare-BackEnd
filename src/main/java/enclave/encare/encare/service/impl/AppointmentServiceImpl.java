package enclave.encare.encare.service.impl;

import enclave.encare.encare.config.TimeConfig;
import enclave.encare.encare.form.AppointmentForm;
import enclave.encare.encare.model.Appointment;
import enclave.encare.encare.model.Doctor;
import enclave.encare.encare.model.Status;
import enclave.encare.encare.model.User;
import enclave.encare.encare.modelResponse.AppointmentResponse;
import enclave.encare.encare.repository.AppointmentRepository;
import enclave.encare.encare.service.AppointmentService;
import enclave.encare.encare.service.DoctorService;
import enclave.encare.encare.service.StatusService;
import enclave.encare.encare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class AppointmentServiceImpl implements AppointmentService {

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    DoctorService doctorService;

    @Autowired
    UserService userService;

    @Autowired
    StatusService statusService;


    @Override
    public AppointmentResponse findById(long id) {
        Appointment appointment = appointmentRepository.findByAppointmentId(id);
        if (appointment==null) return  null;

        return transformData(appointment);
    }

    @Override
    public boolean newAppointment(AppointmentForm appointmentForm) {

        Date day = TimeConfig.getDate(appointmentForm.getDay());
        int time = appointmentForm.getTime();
        if (findTimeAndDay(time, day)){
            User user = new User(appointmentForm.getUserId());
            Doctor doctor = new Doctor(appointmentForm.getDoctorId());
            Status status = new Status(1); // Đang chờ xác nhận

            Appointment appointment = new Appointment();
            appointment.setUser(user);
            appointment.setDoctor(doctor);
            appointment.setTime(time);
            appointment.setDay(day);
            appointment.setDescription(appointmentForm.getDescription());
            appointment.setStatus(status);
            appointment.setSymptoms(appointmentForm.getSymptomps());

            appointmentRepository.save(appointment);
            return true;
        }
        return false;
    }

    public boolean findTimeAndDay(int time, Date date){
//        Appointment appointment = appointmentRepository.findByTimeAndDay(time, date);
        List<Appointment> appointment = appointmentRepository.findByTimeAndDayEquals(time, date);
        if (appointment.size() > 0){
            return false;
        }
        return true;
    }

    private AppointmentResponse transformData(Appointment appointment){
        AppointmentResponse appointmentResponse = new AppointmentResponse();

        appointmentResponse.setAppointmentId(appointment.getAppointmentId());
        appointmentResponse.setSymptoms(appointment.getSymptoms());
        appointmentResponse.setDescription(appointment.getDescription());
        appointmentResponse.setTime(appointment.getTime());
        appointmentResponse.setDay(TimeConfig.getTime(appointment.getDay()));
        appointmentResponse.setCreateDate(TimeConfig.getTime(appointment.getCreateDate()));
        appointmentResponse.setDoctorResponse(doctorService.findById(appointment.getDoctor().getDoctorId()));
        appointmentResponse.setUserResponse(userService.findById(appointment.getUser().getUserId()));
        appointmentResponse.setStatusResponse(statusService.findById(appointment.getStatus().getStatusId()));

        return appointmentResponse;
    }
}
