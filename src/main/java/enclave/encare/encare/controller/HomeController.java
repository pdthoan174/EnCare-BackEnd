package enclave.encare.encare.controller;

import enclave.encare.encare.form.LoginForm;
import enclave.encare.encare.form.RegisterFormDoctor;
import enclave.encare.encare.form.RegisterFormUser;
import enclave.encare.encare.jwt.JwtTokenProvider;
import enclave.encare.encare.model.Account;
import enclave.encare.encare.model.Doctor;
import enclave.encare.encare.model.ResponseObject;
import enclave.encare.encare.model.User;
import enclave.encare.encare.service.CategoryService;
import enclave.encare.encare.service.DoctorService;
import enclave.encare.encare.service.UserService;
import enclave.encare.encare.until.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
public class HomeController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserService userService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    DoctorService doctorService;

    @PostMapping("/login")
    public ResponseEntity<ResponseObject> login(@RequestBody LoginForm loginForm){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginForm.getPhone(),loginForm.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        CustomUserDetail customUserDetail = (CustomUserDetail) authentication.getPrincipal();
        Account account = customUserDetail.getAccount();
        String token = jwtTokenProvider.generateToken(customUserDetail);

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"type: "+account.getRole(), token)
        );
    }

    @PostMapping("/registerUser")
    public ResponseEntity<ResponseObject> registerUser(@RequestBody RegisterFormUser registerFormUser){
        if (userService.register(registerFormUser)){
            return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"Register Success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400,"Register fail", "đã tồn tại số điện thoại này")
        );
    }

    @PostMapping("/registerDoctor")
    public ResponseEntity<ResponseObject> registerDoctor(@RequestBody RegisterFormDoctor registerFormDoctor){
        if (doctorService.register(registerFormDoctor)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200,"Register Success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400,"Register fail", "đã tồn tại số điện thoại này")
        );
    }

    @GetMapping("/listCategory")
    public ResponseEntity<ResponseObject> listCategory(){
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject(200, "List Category", categoryService.listCategory())
        );
    }

    @GetMapping("/listDoctor/categoryId={categoryId}")
    public ResponseEntity<ResponseObject> listDoctorOfCategoryId(@PathVariable("categoryId") long categoryId){
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "List Category", doctorService.listDoctorOfCategory(categoryId))
        );
    }
}
