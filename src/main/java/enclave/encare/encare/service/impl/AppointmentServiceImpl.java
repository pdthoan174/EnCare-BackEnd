package enclave.encare.encare.service.impl;

import enclave.encare.encare.config.TimeConfig;
import enclave.encare.encare.form.AppointmentForm;
//<<<<<<< HEAD
import enclave.encare.encare.form.FreeTimeForm;
import enclave.encare.encare.model.*;
import enclave.encare.encare.modelResponse.AccountResponse;
import enclave.encare.encare.modelResponse.AppointmentResponse;
import enclave.encare.encare.modelResponse.DoctorResponse;
import enclave.encare.encare.modelResponse.UserResponse;
//=======
import enclave.encare.encare.model.Appointment;
import enclave.encare.encare.model.Doctor;
import enclave.encare.encare.model.Status;
import enclave.encare.encare.model.User;
//>>>>>>> doctor
import enclave.encare.encare.repository.AppointmentRepository;
import enclave.encare.encare.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

//<<<<<<< HEAD
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
//=======
import java.text.SimpleDateFormat;
import java.util.stream.Collectors;
//>>>>>>> doctor

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
    public List<AppointmentResponse> findByHospitalId(long hospitalId) {
        List<AppointmentResponse> appointmentResponseList = new ArrayList<>();
        appointmentResponseList = transformData(appointmentRepository.findByDoctor_Hospital_HospitalId(hospitalId));
        return appointmentResponseList;
    }

    @Override
    public List<AppointmentResponse> findByStatusId(long statusId) {
        List<Appointment> appointmentList = appointmentRepository.findByStatusStatusId(statusId);
        if (appointmentList == null) return null;

        return transformData(appointmentList);
    }

    @Override
    public AppointmentResponse findById(long id) {
//<<<<<<< HEAD
        try{
            Appointment appointment = appointmentRepository.findByAppointmentId(id);
            if (appointment!=null){
                return transformData(appointment);
            }
            return null;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean newAppointment(AppointmentForm appointmentForm) {

        Date day = TimeConfig.getDate(appointmentForm.getDay());
        int time = appointmentForm.getTime();
        long userId = userService.findUserIdByAccountId(appointmentForm.getAccountUserId());
        if (findTimeAndDay(appointmentForm.getDoctorId(), time, day)){
            User user = new User(userId);
            Doctor doctor = new Doctor(appointmentForm.getDoctorId());
            Status status = new Status(1); // Đang chờ xác nhận

            Appointment appointment = new Appointment();
            appointment.setUser(user);
            appointment.setDoctor(doctor);
            appointment.setTime(time);
            appointment.setDay(day);
//            appointment.setDescription(appointmentForm.getDescription().trim());
            appointment.setStatus(status);
            appointment.setSymptoms(appointmentForm.getSymptomps().trim());
            appointment.setCreateDate(new Date());

            appointmentRepository.save(appointment);
            return true;
        }
        return false;
    }

    @Override
    public List<AppointmentResponse> historyAppointmentUser(long accountId, int page) {
        long userId = userService.findUserIdByAccountId(accountId);
        User user = new User(userId);
        Pageable pageable = PageRequest.of(page, 6);
        List<Appointment> appointmentList = appointmentRepository.findByUser(user, pageable);
        List<AppointmentResponse> appointmentResponseList = new ArrayList<AppointmentResponse>();
        for (Appointment appointment:appointmentList){
            AppointmentResponse appointmentResponse = transformData(appointment);
            appointmentResponseList.add(appointmentResponse);
        }
        return appointmentResponseList;
    }

    @Override
    public List<Integer> listFreeTime(FreeTimeForm freeTimeForm) {
        Doctor doctor = new Doctor(freeTimeForm.getDoctorId());
        Date date = TimeConfig.getDate(freeTimeForm.getTime());
        boolean check = true;
        List<Appointment> appointmentList = appointmentRepository.findByDoctorAndDay(doctor, date);
        List<Integer> list = new ArrayList<Integer>();
        for (int i = 7; i<=11; i++){
            check = true;
            for (int j=0; j<appointmentList.size(); j++){
                if (i == appointmentList.get(j).getTime()){
                    check = false;
                    break;
                }
            }
            if (check == true){
                list.add(i);
            }
        }
        for (int i = 13; i<=16; i++){
            check = true;
            for (int j=0; j<appointmentList.size(); j++){
                if (i == appointmentList.get(j).getTime()){
                    check = false;
                    break;
                }
            }
            if (check == true){
                list.add(i);
            }
        }

        return list;
    }

    @Override
    public boolean cancelAppointment(long accountId, long appointmentId) {
        long userId = userService.findUserIdByAccountId(accountId);
        User user = new User(userId);
        Appointment appointment = appointmentRepository.findByAppointmentIdAndUser(appointmentId, user);
        if (appointment == null){
            return false;
        }
        Status status = new Status(4);
        appointment.setStatus(status);
        appointmentRepository.save(appointment);
        return true;
    }

    @Override
    public List<AppointmentResponse> findAll() {
        List<Appointment> appointmentList = appointmentRepository.findAll();
        List<AppointmentResponse> appointmentResponseList = new ArrayList<AppointmentResponse>();
        for (Appointment appointment:appointmentList){
            AppointmentResponse appointmentResponse = transformData(appointment);
            appointmentResponseList.add(appointmentResponse);
        }
        return appointmentResponseList;
    }

    public boolean findTimeAndDay(long doctorId, int time, Date date){
//        Appointment appointment = appointmentRepository.findByTimeAndDay(time, date);
        Doctor doctor = new Doctor(doctorId);
        List<Appointment> appointment = appointmentRepository.findByDoctorAndTimeAndDayEquals(doctor, time, date);
        if (appointment.size() > 0){
            return false;
        }
        return true;
//=======
//        Appointment appointment = appointmentRepository.findByAppointmentId(id);
//        if (appointment == null) return null;
//
//        return transformData(appointment);
    }

    @Override
    public List<AppointmentResponse> findByPhone(String phone) {
//        List<Appointment> appointmentList = appointmentRepository.findAll()
//                .stream().filter(appointment -> appointment.getUser().getAccount().getPhone().contains(phone)).collect(Collectors.toList());
        List<Appointment> appointmentList = appointmentRepository.findByUser_Account_PhoneContains(phone);
        if (appointmentList == null) return null;

        return transformData(appointmentList);
//>>>>>>> doctor
    }

    @Override
    public List<AppointmentResponse> findByStatus(Long statusId) {
        return null;
    }

    @Override
    public String setDescription(long id, String description) {
        Appointment appointment = appointmentRepository.findByAppointmentId(id);
        if (appointment == null)
            return "Appointment is not exist !";
        else if (appointment.getStatus().getStatusId()>3) {
            appointment.setDescription(description);
            appointmentRepository.save(appointment);
            return "Success";
        }
        return "Appointment must be in confirmed status";
    }

    @Override
    public boolean isExistTime(int time, Date date, long appointmentId) {
        List<Appointment> appointmentList = appointmentRepository.findAll();
//        appointmentList.removeIf(appointment -> {
//            long statusId = appointment.getStatus().getStatusId();
//            return  statusId!=5 || statusId!=1 ;
//        });
        if (appointmentList == null)
            return false;
        DoctorResponse doctorResponse = doctorService.findById(appointmentId);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for (Appointment appointment : appointmentList) {
            if (appointment.getAppointmentId() == appointmentId)
                continue;
            if (formatter.format(appointment.getDay()).equals(formatter.format(date))
                    && appointment.getTime() == time
                    && (appointment.getStatus().getStatusId() > 1 && appointment.getStatus().getStatusId() < 5)
                    && appointment.getAppointmentId() != appointmentId
                    && appointment.getDoctor().getDoctorId() == doctorResponse.getDoctorId()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isExistTime(long appointmentId) {
        Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId);
        if (appointment == null) return true;
        List<Appointment> appointmentList = appointmentRepository.findByDoctorDoctorId(appointment.getDoctor().getDoctorId());
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        for (Appointment a : appointmentList) {
            if (a.getAppointmentId() != appointmentId
                    && a.getTime() == appointment.getTime()
                    && formatter.format(a.getDay()).equals(formatter.format(appointment.getDay()))
                    && (a.getStatus().getStatusId() > 1 && a.getStatus().getStatusId() < 5)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<AppointmentResponse> findByDoctorId(long doctorId) {
        List<AppointmentResponse> appointmentResponseList = new ArrayList<>();
        List<Appointment> appointmentList = appointmentRepository.findByDoctorDoctorId(doctorId);
        if (appointmentList != null) {
            appointmentResponseList = transformData(appointmentList);
        }
        return appointmentResponseList;
    }

    public boolean findTimeAndDay(int time, Date date) {
//        Appointment appointment = appointmentRepository.findByTimeAndDay(time, date);
        List<Appointment> appointment = appointmentRepository.findByTimeAndDayEquals(time, date);
        if (appointment.size() > 0) {
            return false;
        }
        return true;
    }

    private AppointmentResponse transformData(Appointment appointment) {
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

    private List<AppointmentResponse> transformData(List<Appointment> appointmentList) {

        List<AppointmentResponse> appointmentResponseList = new ArrayList<>();
        for (Appointment appointment : appointmentList) {
            appointmentResponseList.add(transformData(appointment));
        }
        return appointmentResponseList;
    }
}
