package enclave.encare.encare.service.impl;

import enclave.encare.encare.form.RegisterFormDoctor;
import enclave.encare.encare.model.*;
import enclave.encare.encare.modelResponse.CategoryResponse;
import enclave.encare.encare.modelResponse.DoctorResponse;
import enclave.encare.encare.modelResponse.HospitalResponse;
import enclave.encare.encare.repository.AppointmentRepository;
import enclave.encare.encare.repository.DoctorRepository;
import enclave.encare.encare.repository.HospitalRepository;
import enclave.encare.encare.repository.StatusRepository;
import enclave.encare.encare.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    HospitalRepository hospitalRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    HospitalService hospitalService;

    @Autowired
    AppointmentRepository appointmentRepository;


    @Autowired
    StatusRepository statusRepository;



    @Override
    public DoctorResponse findById(long id) {
        Doctor doctor = doctorRepository.findByDoctorId(id);
        if (doctor==null)
            return null;
        return transformData(doctor);
    }

    @Override
    public DoctorResponse findByName(String name) {
        Doctor doctor = doctorRepository.findByAccount_Name(name);
        if (doctor==null)
            return null;
        return transformData(doctor);
    }

    @Override
    public boolean register(RegisterFormDoctor registerFormDoctor) {
        long id = accountService.registerDoctor(registerFormDoctor);
        if (id != 0){
            Account account = new Account(id);
            Category category = new Category(registerFormDoctor.getCategoryId());
            Hospital hospital = new Hospital(registerFormDoctor.getHospitalId());

            Doctor doctor = new Doctor();
            doctor.setRating(0);
            doctor.setCountRating(0);
            doctor.setAccount(account);
            doctor.setCategory(category);
            doctor.setHospital(hospital);

            doctorRepository.save(doctor);
            return true;
        }
        return false;
    }

    @Override
    public boolean changeStatusAppointment(long appointmentId,int statusId) {
        Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId);
        Status status = new Status(statusId);
        appointment.setStatus(status);
        appointmentRepository.save(appointment);
        return true;
    }

    @Override
    public List<DoctorResponse> listDoctor() {
        List<Doctor> doctorList = doctorRepository.findAll();
        List<DoctorResponse> doctorResponseList = new ArrayList<>();
        if (doctorList==null) return null;
        doctorResponseList = transformData(doctorList);
        return doctorResponseList;
    }

    @Override
    public List<DoctorResponse> listDoctor(int page) {
        Pageable pageable = PageRequest.of(page, 6);
        List<Doctor> doctorList = doctorRepository.findAllByAccountExistsOrderByDoctorIdDesc(pageable);
        if (doctorList==null) return  null;
        List<DoctorResponse> doctorResponseList = transformData(doctorList);
        return doctorResponseList;
    }

    @Override
    public List<DoctorResponse> listDoctorOfHospital(long hospitalId) {
        if (hospitalService.findById(hospitalId)==null) return  null;
        Hospital hospital = new Hospital(hospitalId);
        List<Doctor> doctorList = doctorRepository.findDoctorByHospitalOrderByRatingDesc(hospital);
        List<DoctorResponse> doctorResponseList = new ArrayList<DoctorResponse>();
        doctorResponseList = transformData(doctorList);
        return doctorResponseList;
    }

    @Override
    public List<DoctorResponse> listDoctorOfHospital(long hospitalId, int page) {
        Hospital hospital = new Hospital(hospitalId);
        Pageable pageable = PageRequest.of(page, 6);
        List<Doctor> doctorList = doctorRepository.findDoctorByHospitalOrderByRatingDesc(hospital, pageable);
        List<DoctorResponse> doctorResponseList = new ArrayList<DoctorResponse>();
        doctorResponseList = transformData(doctorList);
        return doctorResponseList;
    }

    @Override
    public List<DoctorResponse> listDoctorOfCategory(long categoryId) {
        if (categoryService.findById(categoryId)==null) return  null;
        Category category = new Category(categoryId);
        List<Doctor> doctorList = doctorRepository.findDoctorByCategoryOrderByRatingDesc(category);
        List<DoctorResponse> doctorResponseList = new ArrayList<DoctorResponse>();
        for (Doctor doctor:doctorList){
            DoctorResponse doctorResponse = transformData(doctor);
            doctorResponseList.add(doctorResponse);
        }
        return doctorResponseList;
    }

    @Override
    public List<DoctorResponse> listDoctorOfCategory(long categoryId, int page) {
        Category category = new Category(categoryId);
        Pageable pageable = PageRequest.of(page, 6);
        List<Doctor> doctorList = doctorRepository.findDoctorByCategoryOrderByRatingDesc(category, pageable);
        List<DoctorResponse> doctorResponseList = new ArrayList<DoctorResponse>();
        for (Doctor doctor:doctorList){
            DoctorResponse doctorResponse = transformData(doctor);
            doctorResponseList.add(doctorResponse);
        }
        return doctorResponseList;
    }



    private DoctorResponse transformData(Doctor doctor){
        DoctorResponse doctorResponse = new DoctorResponse();

        doctorResponse.setDoctorId(doctor.getDoctorId());
        doctorResponse.setRating(doctor.getRating());
        doctorResponse.setCountRating(doctor.getCountRating());

        doctorResponse.setAccountResponse(accountService.findById(doctor.getAccount().getAccountId()));
        doctorResponse.setCategoryResponse(categoryService.findById(doctor.getCategory().getCategoryId()));
        doctorResponse.setHospitalResponse(hospitalService.findById(doctor.getHospital().getHospitalId()));

        return doctorResponse;
    }
    private List<DoctorResponse> transformData(List<Doctor> listDoctor){
        List<DoctorResponse> doctorResponseList = new ArrayList<>();
        for (Doctor doctor: listDoctor) {
            doctorResponseList.add(transformData(doctor));
        }
        return doctorResponseList;
    }
}
