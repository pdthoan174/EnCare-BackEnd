package enclave.encare.encare.service.impl;

import enclave.encare.encare.config.TimeConfig;
import enclave.encare.encare.model.Appointment;
import enclave.encare.encare.modelResponse.AppointmentResponse;
import enclave.encare.encare.repository.AppointmentRepository;
import enclave.encare.encare.service.AppointmentService;
import enclave.encare.encare.service.DoctorService;
import enclave.encare.encare.service.StatusService;
import enclave.encare.encare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
        return transformData(appointmentRepository.findByAppointmentId(id));
    }

    private AppointmentResponse transformData(Appointment appointment){
        AppointmentResponse appointmentResponse = new AppointmentResponse();

        appointmentResponse.setAppointmentId(appointment.getAppointmentId());
        appointmentResponse.setSymptoms(appointment.getSymptoms());
        appointmentResponse.setDescription(appointment.getDescription());
        appointmentResponse.setTime(TimeConfig.getTime(appointment.getTime()));
        appointmentResponse.setCreateDate(TimeConfig.getTime(appointment.getCreateDate()));
        appointmentResponse.setDoctorResponse(doctorService.findById(appointment.getDoctor().getDoctorId()));
        appointmentResponse.setUserResponse(userService.findById(appointment.getUser().getUserId()));
        appointmentResponse.setStatusResponse(statusService.findById(appointment.getStatus().getStatusId()));

        return appointmentResponse;
    }
}
