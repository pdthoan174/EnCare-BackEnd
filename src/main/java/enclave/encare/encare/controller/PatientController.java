package enclave.encare.encare.controller;

import enclave.encare.encare.config.RegexConfig;
import enclave.encare.encare.config.TimeConfig;
import enclave.encare.encare.form.*;
import enclave.encare.encare.jwt.JwtTokenProvider;
import enclave.encare.encare.model.ResponseObject;
import enclave.encare.encare.modelResponse.AppointmentResponse;
import enclave.encare.encare.service.*;
import enclave.encare.encare.until.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/patient")
public class PatientController {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserService userService;

    @Autowired
    AccountService accountService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    FeedbackService feedbackService;

    @Autowired
    DoctorService doctorService;



    @PostMapping("/newAppointment")
    public ResponseEntity<ResponseObject> newAppointment(@Valid @RequestBody AppointmentForm appointmentForm){
        appointmentForm.setAccountUserId(getAccountId());
        if (doctorService.findById(appointmentForm.getDoctorId())==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "This doctor does not exist", "")
            );
        }
        if (!appointmentForm.getDay().matches(RegexConfig.day)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Day is not correct format", "")
            );
        }

        if (appointmentService.newAppointment(appointmentForm)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "New appointment success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "The time you selected has been scheduled", "")
        );
    }

    @PostMapping("/feedback")
    public ResponseEntity<ResponseObject> feedback(@Valid @RequestBody FeedbackForm feedbackForm){
        feedbackForm.setAccountUserId(getAccountId());
        if (appointmentService.findById(feedbackForm.getAppointmentId())==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "This appointment does not exist", "")
            );
        }
        if(feedbackService.newFeedback(feedbackForm)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200,"Feedback success","")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new ResponseObject(400, "Feedback fail","This appointment already feedback")
        );
    }

    @GetMapping("/history")
    public ResponseEntity<ResponseObject> historyAppointment(@RequestParam(required = false, name = "page", defaultValue = "0") int page){
        long accountId = getAccountId();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"History appointment", appointmentService.historyAppointmentUser(accountId, page))
        );
    }

    @GetMapping("/cancel")
    public ResponseEntity<ResponseObject> cancelAppointment(@RequestParam(required = true, name = "appointmentId") long appoinmentId){
        if (appointmentService.findById(appoinmentId)==null){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "This appointment does not exist", "")
            );
        }
        appointmentService.cancelAppointment(getAccountId(), appoinmentId);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject(200, "Cancel Appointment success", "")
        );
    }

    @PostMapping("/uploadAvatar")
    public ResponseEntity<ResponseObject> uploadAvatar(@Valid @ModelAttribute("imageForm") ImageForm imageForm){
        imageForm.setAccountId(getAccountId());
        if (imageForm.getFile().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400,"You haven't selected a photo yet","")
            );
        }
        userService.uploadAvatar(imageForm);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"Update avatar success","")
        );
    }

    @PostMapping("/newPassword")
    public ResponseEntity<ResponseObject> newPassowrd(@Valid @RequestBody NewPasswordForm newPasswordForm){
        newPasswordForm.setAccountId(getAccountId());
        if(accountService.updatePassword(newPasswordForm)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200,"Update new password success","")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400,"Update new password fail","Old password fail")
        );
    }

    private long getAccountId(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication!=null){
            String token = jwtTokenProvider.generateToken((CustomUserDetail) authentication.getPrincipal());
            return jwtTokenProvider.getUserId(token);
        }
        return 0;
    }
}
