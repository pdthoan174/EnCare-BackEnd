package enclave.encare.encare.service.impl;

import enclave.encare.encare.form.RegisterFormDoctor;
import enclave.encare.encare.model.Account;
import enclave.encare.encare.model.Category;
import enclave.encare.encare.model.Doctor;
import enclave.encare.encare.model.Hospital;
import enclave.encare.encare.modelResponse.DoctorResponse;
import enclave.encare.encare.repository.DoctorRepository;
import enclave.encare.encare.service.AccountService;
import enclave.encare.encare.service.CategoryService;
import enclave.encare.encare.service.DoctorService;
import enclave.encare.encare.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    DoctorRepository doctorRepository;

    @Autowired
    AccountService accountService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    HospitalService hospitalService;



    @Override
    public DoctorResponse findById(long id) {
        return transformData(doctorRepository.findByDoctorId(id));
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
    public List<DoctorResponse> listDoctorOfCategory(long categoryId) {
        Category category = new Category(categoryId);
        List<Doctor> doctorList = doctorRepository.findDoctorByCategory(category);
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
}
