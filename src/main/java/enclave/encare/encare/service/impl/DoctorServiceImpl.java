package enclave.encare.encare.service.impl;

import enclave.encare.encare.config.TimeConfig;
import enclave.encare.encare.form.DoctorInformationForm;
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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

@Service
public class DoctorServiceImpl implements DoctorService {

    @Autowired
    PasswordEncoder passwordEncoder;

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
        if (doctor == null)
            return null;
        return transformData(doctor);
    }

    @Override
    public DoctorResponse findByName(String name) {
        Doctor doctor = doctorRepository.findByAccount_Name(name);
        if (doctor == null)
            return null;
        return transformData(doctor);
    }

    @Override
    public String updateInfor(DoctorInformationForm doctorInformationForm) throws ParseException {

        Doctor current = doctorRepository.findByDoctorId(doctorInformationForm.getDoctorId());
        String result = "";
        if (current != null) {
            Account currentAccount = current.getAccount();
            Pattern name_pattern = Pattern.compile("^[a-zA-Z\\\\s]+");
            String name = doctorInformationForm.getName();
            if ((name == null || name.length() <1) || !name_pattern.matcher(name).matches()) {
                return "Invalid field name.";
            }
            currentAccount.setName(name);
            String stringDate = doctorInformationForm.getBirthDay();
            TimeConfig timeConfig = new TimeConfig();
            Date birthDay = timeConfig.getDate(stringDate);
            Pattern birthDay_pattern = Pattern.compile("^((0[1-9]|[12][0-9])/02|(0[1-9]|[12][0-9]|30)/(0[469]|11)|(0[1-9]|[12][0-9]|3[01])/(0[13578]|1[02]))/\\d{4}");
            if (!birthDay_pattern.matcher(stringDate).matches() || birthDay == null) {
                return "Invalid field birthDay.";
            }
            currentAccount.setBirthday(birthDay);
            Pattern password_pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$");
            String password = doctorInformationForm.getPassword();
            if ((password == null || password.length() < 0) || !password_pattern.matcher(password).matches()) {
                return "Invalid field passWord.";
            }
            currentAccount.setPassword(passwordEncoder.encode(password));
            String phone = doctorInformationForm.getPhone();
            Pattern phone_pattern = Pattern.compile("([+]84|0[3|5|7|8|9])+([0-9]{8,10})\\b");
            if (phone == null || !phone_pattern.matcher(phone).matches()) {
                return "Invalid field phone.";
            }
            if (!accountService.findByPhone(phone) && !phone.equals(currentAccount.getPhone())){
                return "Phone is exist !";
            }
            currentAccount.setPhone(phone);
            String descriptions = doctorInformationForm.getDescription();
            if (descriptions != null || descriptions.length() > 0) {
                currentAccount.setDescription(descriptions);
            }

            currentAccount.setAvatar(doctorInformationForm.getAvatar());
            currentAccount.setUpdateDate(new Date());
            current.setAccount(currentAccount);
            long hospitalId = doctorInformationForm.getHospitalId();
            if (hospitalService.findById(hospitalId) == null) {
                return "hospital is not exist !";
            }
            current.setHospital(new Hospital(hospitalId));
            long categoryId = doctorInformationForm.getCategoryId();
            if (categoryService.findById(categoryId) == null) {
                return "Category is not exist !";
            }
            current.setCategory(new Category(doctorInformationForm.getCategoryId()));
            doctorRepository.save(current);
            return "Update success.";
        }
        return "Account is not exist !!!";
    }

    @Override
    public boolean register(RegisterFormDoctor registerFormDoctor) {
        long id = accountService.registerDoctor(registerFormDoctor);
        if (id != 0) {
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
    public boolean changeStatusAppointment(long appointmentId, int statusId) {
        Appointment appointment = appointmentRepository.findByAppointmentId(appointmentId);
        if (appointment != null) {
            Status status = new Status(statusId);
            appointment.setStatus(status);
            appointmentRepository.save(appointment);
            return true;
        }
        return false;
    }

    @Override
    public List<DoctorResponse> listDoctor() {
        List<Doctor> doctorList = doctorRepository.findAll();
        List<DoctorResponse> doctorResponseList = new ArrayList<>();
        if (doctorList == null) return null;
        doctorResponseList = transformData(doctorList);
        return doctorResponseList;
    }

    @Override
    public List<DoctorResponse> listDoctor(int page) {
        Pageable pageable = PageRequest.of(page, 6);
        List<Doctor> doctorList = doctorRepository.findAllByAccountExistsOrderByDoctorIdDesc(pageable);
        if (doctorList == null) return null;
        List<DoctorResponse> doctorResponseList = transformData(doctorList);
        return doctorResponseList;
    }

    @Override
    public List<DoctorResponse> listDoctorOfHospital(long hospitalId) {
        if (hospitalService.findById(hospitalId) == null) return null;
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
        if (categoryService.findById(categoryId) == null) return null;
        Category category = new Category(categoryId);
        List<Doctor> doctorList = doctorRepository.findDoctorByCategoryOrderByRatingDesc(category);
        List<DoctorResponse> doctorResponseList = new ArrayList<DoctorResponse>();
        for (Doctor doctor : doctorList) {
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
        for (Doctor doctor : doctorList) {
            DoctorResponse doctorResponse = transformData(doctor);
            doctorResponseList.add(doctorResponse);
        }
        return doctorResponseList;
    }


    private DoctorResponse transformData(Doctor doctor) {
        DoctorResponse doctorResponse = new DoctorResponse();

        doctorResponse.setDoctorId(doctor.getDoctorId());
        doctorResponse.setRating(doctor.getRating());
        doctorResponse.setCountRating(doctor.getCountRating());

        doctorResponse.setAccountResponse(accountService.findById(doctor.getAccount().getAccountId()));
        doctorResponse.setCategoryResponse(categoryService.findById(doctor.getCategory().getCategoryId()));
        doctorResponse.setHospitalResponse(hospitalService.findById(doctor.getHospital().getHospitalId()));

        return doctorResponse;
    }

    private List<DoctorResponse> transformData(List<Doctor> listDoctor) {
        List<DoctorResponse> doctorResponseList = new ArrayList<>();
        for (Doctor doctor : listDoctor) {
            doctorResponseList.add(transformData(doctor));
        }
        return doctorResponseList;
    }
}
