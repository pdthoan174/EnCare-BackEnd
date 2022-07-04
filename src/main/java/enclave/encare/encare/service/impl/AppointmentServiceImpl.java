package enclave.encare.encare.service.impl;

import enclave.encare.encare.config.TimeConfig;
import enclave.encare.encare.form.AppointmentForm;
import enclave.encare.encare.model.Appointment;
import enclave.encare.encare.model.Doctor;
import enclave.encare.encare.model.Status;
import enclave.encare.encare.model.User;
import enclave.encare.encare.modelResponse.AppointmentResponse;
import enclave.encare.encare.modelResponse.DoctorResponse;
import enclave.encare.encare.repository.AppointmentRepository;
import enclave.encare.encare.service.AppointmentService;
import enclave.encare.encare.service.DoctorService;
import enclave.encare.encare.service.StatusService;
import enclave.encare.encare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<AppointmentResponse> findAll() {
        List<AppointmentResponse> appointmentResponseList = new ArrayList<>();
        appointmentResponseList = transformData(appointmentRepository.findAll());
        return appointmentResponseList;
    }

    @Override
    public List<AppointmentResponse> findByHospitalId(long hospitalId) {
        List<AppointmentResponse> appointmentResponseList = new ArrayList<>();
        appointmentResponseList = transformData(appointmentRepository.findByDoctor_Hospital_HospitalId(hospitalId));
        return appointmentResponseList;
    }

    @Override
    public List<AppointmentResponse> findByStatusId(long statusId) {
        List<Appointment> appointmentList = appointmentRepository.findByStatusStatusId(statusId);
        if (appointmentList==null) return  null;

        return transformData(appointmentList);
    }

    @Override
    public AppointmentResponse findById(long id) {
        Appointment appointment = appointmentRepository.findByAppointmentId(id);
        if (appointment==null) return  null;

        return transformData(appointment);
    }

    @Override
    public List<AppointmentResponse> findByPhone(String phone) {
//        List<Appointment> appointmentList = appointmentRepository.findAll()
//                .stream().filter(appointment -> appointment.getUser().getAccount().getPhone().contains(phone)).collect(Collectors.toList());
        List<Appointment> appointmentList = appointmentRepository.findByUser_Account_PhoneContains(phone);
        if (appointmentList==null) return  null;

        return transformData(appointmentList);
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

    @Override
    public boolean setDescription(long id, String description) {
        Appointment appointment = appointmentRepository.findByAppointmentId(id);
        if (appointment!=null && (appointment.getStatus().getStatusId()==3 || appointment.getStatus().getStatusId()==5))
        {
            appointment.setDescription(description);
            appointmentRepository.save(appointment);
            return true;
        }
        return false;
    }

    @Override
    public boolean isExistTime(int time, Date date,long appointmentId) {
        List<Appointment> appointmentList = appointmentRepository.findAll();
//        appointmentList.removeIf(appointment -> {
//            long statusId = appointment.getStatus().getStatusId();
//            return  statusId!=5 || statusId!=1 ;
//        });
        if (appointmentList==null)
            return false;
        DoctorResponse doctorResponse = doctorService.findById(appointmentId);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for (Appointment appointment : appointmentList)
        {
            if (appointment.getAppointmentId()==appointmentId)
                continue;
            if (formatter.format(appointment.getDay()).equals(formatter.format(date))
                    && appointment.getTime()==time
                    && (appointment.getStatus().getStatusId()>1 && appointment.getStatus().getStatusId() <5)
                    && appointment.getAppointmentId()!=appointmentId
                    && appointment.getDoctor().getDoctorId() == doctorResponse.getDoctorId()){
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isExistTime(long appointmentId) {
        Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId);
        if (appointment==null ) return true;
        List<Appointment> appointmentList = appointmentRepository.findByDoctorDoctorId(appointment.getDoctor().getDoctorId());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for (Appointment a: appointmentList){
            if (a.getAppointmentId()!=appointmentId
                    && a.getTime() == appointment.getTime()
                    && formatter.format(a.getDay()).equals(formatter.format(appointment.getDay()))
                    && (a.getStatus().getStatusId()>1 && a.getStatus().getStatusId()<5)){
            return true;
            }
        }

        return false;
    }

    @Override
    public List<AppointmentResponse> findByDoctorId(long doctorId) {
        List<AppointmentResponse> appointmentResponseList = new ArrayList<>();
        List<Appointment> appointmentList = appointmentRepository.findByDoctorDoctorId(doctorId);
        if (appointmentList != null){
            appointmentResponseList = transformData(appointmentList);
        }
        return appointmentResponseList;
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
    private List<AppointmentResponse> transformData(List<Appointment> appointmentList){

        List<AppointmentResponse> appointmentResponseList = new ArrayList<>();
        for (Appointment appointment: appointmentList){
            appointmentResponseList.add(transformData(appointment));
        }
        return appointmentResponseList;
    }
}
