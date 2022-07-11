package enclave.encare.encare.service.impl;

import enclave.encare.encare.config.NumberConfig;
import enclave.encare.encare.form.RegisterFormDoctor;
import enclave.encare.encare.form.mapbox.Distance;
import enclave.encare.encare.form.mapbox.Location;
import enclave.encare.encare.model.Account;
import enclave.encare.encare.model.Category;
import enclave.encare.encare.model.Doctor;
import enclave.encare.encare.model.Hospital;
import enclave.encare.encare.modelResponse.DoctorResponse;
import enclave.encare.encare.repository.AppointmentRepository;
import enclave.encare.encare.repository.DoctorRepository;
import enclave.encare.encare.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    AppointmentRepository appointmentRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    HospitalService hospitalService;

    @Autowired
    MapboxService mapboxService;

    @Override
    public DoctorResponse findById(long id) {
        try {
            Doctor doctor = doctorRepository.findByDoctorId(id);
            if (doctor != null){
                return transformData(doctor);
            }
            return null;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public long findDoctorIdByAccountId(long accountId) {
        System.out.println("find doctorId by account id");
        return doctorRepository.findDoctorByAccountId(accountId).getDoctorId();
    }

    @Override
    public boolean register(RegisterFormDoctor registerFormDoctor) {
        long id = accountService.registerDoctor(registerFormDoctor);
        if (id != 0){
            Account account = new Account(id);
            Category category = new Category(registerFormDoctor.getCategoryId());
            Hospital hospital = new Hospital(registerFormDoctor.getHospitalId());

            Doctor doctor = new Doctor();
            doctor.setRating(0f);
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
    public List<DoctorResponse> listDoctorOfCategoryRating(long categoryId, int page, float rating, double lon, double lat) {
        Pageable pageable = PageRequest.of(page, 6);
        List<Doctor> doctorList = doctorRepository.findDoctorByCategoryAndRatingDesc(categoryId, rating, pageable);
        List<DoctorResponse> doctorResponseList = new ArrayList<DoctorResponse>();
        if (lon != 0 && lat!=0){
            Location start = new Location(lon, lat);
            for (Doctor doctor:doctorList){
                DoctorResponse doctorResponse = transformData(doctor);
                Location end = new Location(doctorResponse.getHospitalResponse().getLongMap(), doctorResponse.getHospitalResponse().getLatMap());
                doctorResponse.setDistance(mapboxService.getDistance(start, end));
                doctorResponseList.add(doctorResponse);
            }
        } else {
            for (Doctor doctor:doctorList){
                DoctorResponse doctorResponse = transformData(doctor);
                doctorResponseList.add(doctorResponse);
            }
        }

        return doctorResponseList;
    }

    @Override
    public void updateRating(long appointmentId, float number) {
        Doctor doctor = appointmentRepository.findDoctorByAppointmentId(appointmentId);
        long count = doctor.getCountRating();
        float rating = doctor.getRating();
        float value = (count*rating+number)/(count+1);
        doctor.setRating(value);
        doctor.setCountRating(count+1);

        doctorRepository.save(doctor);
    }

    @Override
    public List<DoctorResponse> findLikeName(String name, int page) {
        Pageable pageable = PageRequest.of(page, 6);
        List<Doctor> doctorList = doctorRepository.findDoctorByName(name, pageable);
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
        doctorResponse.setRating(NumberConfig.round(doctor.getRating()));
        doctorResponse.setCountRating(doctor.getCountRating());

        doctorResponse.setAccountResponse(accountService.findById(doctor.getAccount().getAccountId()));
        doctorResponse.setCategoryResponse(categoryService.findById(doctor.getCategory().getCategoryId()));
        doctorResponse.setHospitalResponse(hospitalService.findById(doctor.getHospital().getHospitalId()));

        return doctorResponse;
    }
}
