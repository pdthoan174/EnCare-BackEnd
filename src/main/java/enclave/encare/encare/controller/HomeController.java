package enclave.encare.encare.controller;

import enclave.encare.encare.config.RegexConfig;
import enclave.encare.encare.form.*;
import enclave.encare.encare.jwt.JwtTokenProvider;
import enclave.encare.encare.model.*;
import enclave.encare.encare.modelResponse.AppointmentResponse;
import enclave.encare.encare.modelResponse.CategoryResponse;
import enclave.encare.encare.modelResponse.LoginResponse;
import enclave.encare.encare.modelResponse.RegisterResponse;
import enclave.encare.encare.service.*;
import enclave.encare.encare.until.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/user")
public class HomeController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    HospitalService hospitalService;

    @Autowired
    StatusService statusService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    MapboxService mapboxService;

    @Autowired
    SmsService smsService;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@Valid @RequestBody LoginForm loginForm){

        if (!loginForm.getPhone().matches(RegexConfig.phone)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400,"login fail", "Phone number is not in the correct format")
            );
        }

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        "+84"+Long.parseLong(loginForm.getPhone().trim()),
                        loginForm.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        Account account = customUserDetail.getAccount();
        String token = jwtTokenProvider.generateToken(customUserDetail);
        LoginResponse loginResponse = new LoginResponse(account.getAccountId(), account.getRole(), account.getPassword(), token);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"Login success", loginResponse)
        );
    }

    @PostMapping("/registerUser")
    public ResponseEntity<ResponseObject> registerUser(@Valid @RequestBody RegisterFormUser registerFormUser){
        if (!registerFormUser.getPhone().matches(RegexConfig.phone)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400,"Register fail", "Phone number is not in the correct format")
            );
        }
        if (!registerFormUser.getName().matches(RegexConfig.name)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400,"Register fail", "Name is not in the correct format")
            );
        }
        if (userService.register(registerFormUser)){
            RegisterResponse registerResponse = new RegisterResponse(
                    registerFormUser.getName(),
                    "+84"+Long.parseLong(registerFormUser.getPhone().trim()),
                    passwordEncoder.encode(registerFormUser.getPassword())
            );

            return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"Register Success", registerResponse)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400,"Register fail", "This phone number already exists")
        );
    }

    @PostMapping("/registerDoctor")
    public ResponseEntity<ResponseObject> registerDoctor(@Valid @RequestBody RegisterFormDoctor registerFormDoctor){
        if (!registerFormDoctor.getPhone().matches(RegexConfig.phone)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400,"Register fail", "Phone number is not in the correct format")
            );
        }
        if (!registerFormDoctor.getName().matches(RegexConfig.name)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400,"Register fail", "Name is not in the correct format")
            );
        }
        if (!registerFormDoctor.getBirthDay().matches(RegexConfig.day)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400,"Register fail", "Birthday is not in the correct format")
            );
        }

        if (doctorService.register(registerFormDoctor)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200,"Register Success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400,"Register fail", "This phone number already exists")
        );
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseObject> update(@Valid @RequestBody InformationForm informationForm){
        if (!informationForm.getBirthDay().isBlank()){
            if (!informationForm.getBirthDay().matches(RegexConfig.day)){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject(400,"Update Information fail", "Birthday is not in the correct format")
                );
            }
        }

        informationForm.setAccountId(getAccountId());
        if (accountService.updateInformation(informationForm)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Update Information success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "The date of birth exceeds the current time", "")
        );
    }

    @GetMapping("/listCategory")
    public ResponseEntity<ResponseObject> listCategory(){
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject(200, "List Category", categoryService.listCategory())
        );
    }

    @GetMapping("/listDoctor")
    public ResponseEntity<ResponseObject> listDoctorOfCategoryIdAndRating(
            @RequestParam(required = true, name = "categoryId") long categoryId,
            @RequestParam(required = false, name = "lon", defaultValue = "0") double lon,
            @RequestParam(required = false, name = "lat", defaultValue = "0") double lat,
            @RequestParam(required = false, name = "page", defaultValue = "0") int page,
            @RequestParam(required = false, name = "rating", defaultValue = "0") int rating){

        CategoryResponse categoryResponse = categoryService.findById(categoryId);
        if (categoryResponse==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "This category does not exist", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(200, "List Category", doctorService.listDoctor())
                new ResponseObject(200, "List Category", doctorService.listDoctorOfCategoryRating(categoryId, page, rating, lon, lat))
        );
    }

    @PostMapping("/listFreeTime")
    public ResponseEntity<ResponseObject> listFreeTimeOfDoctor(@Valid @RequestBody FreeTimeForm freeTimeForm){
        if (!freeTimeForm.getTime().matches(RegexConfig.day)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Day is not correct format", "")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "List free time", appointmentService.listFreeTime(freeTimeForm))
        );
    }

    @GetMapping("/appointment")
    public ResponseEntity<ResponseObject> informationAppointment(@RequestParam(required = true, name = "id", defaultValue = "0") long appointmentId){
        AppointmentResponse appointmentResponse = appointmentService.findById(appointmentId);
        System.out.println(appointmentId);
        if (appointmentResponse!=null){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Information Appointment", appointmentResponse)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Find fail", "Don't have appointment id")
        );
    }


//    @GetMapping("/check")
//    public ResponseEntity<ResponseObject> check(){
////        List<Category> categoryList = new ArrayList<Category>();
////        categoryList.add(new Category("Neuroscience", "Obtain effective methods of treatment and rehabilitation in the department of neurosurgery and general neurology."));
////        categoryList.add(new Category("Orthopaedic Surgery & Sports Medicine",
////                "Restore mobility thanks to our team of experienced chiropractors (bone and muscle)."));
////        categoryList.add(new Category("Oncology Department",
////                "Get diagnoses and treatments from our qualified specialists in various areas of oncology."));
////        categoryList.add(new Category("Pediatrics",
////                "Bring your child the best medical treatment with our highly qualified pediatricians."));
////        categoryList.add(new Category("Department of Otolaryngology",
////                "Consult with specialists with an excellent track record of treating ENT diseases."));
////        categoryList.add(new Category("Ophthalmology",
////                "Get the right eye treatment using accurate surgical devices and make an effective diagnosis."));
////        categoryService.saveAll(categoryList);
////
////        List<Status> statusList = new ArrayList<Status>();
////        statusList.add(new Status("Wait for confirmation", ""));
////        statusList.add(new Status("Confirmed", ""));
////        statusList.add(new Status("Checked", ""));
////        statusList.add(new Status("Cancelled", ""));
////        statusService.saveAll(statusList);
////
////        List<Hospital> hospitalList = new ArrayList<Hospital>();
////        hospitalList.add(new Hospital(
////                "Da Nang General Hospital",
////                0,
////                0,
////                0,
////                0,
////                "Da Nang",
////                "Da Nang General Hospital"
////        ));
////        hospitalService.saveAll(hospitalList);
////        categoryService.delete();
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(200, "Find fail", "")
//        );
//    }

    private long getAccountId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null){
            String token = jwtTokenProvider.generateToken((CustomUserDetail) authentication.getPrincipal());
            return jwtTokenProvider.getUserId(token);
        }
        return 0;
    }
}
