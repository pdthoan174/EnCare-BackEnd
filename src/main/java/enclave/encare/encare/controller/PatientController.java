package enclave.encare.encare.controller;

import enclave.encare.encare.form.*;
import enclave.encare.encare.jwt.JwtTokenProvider;
import enclave.encare.encare.model.ResponseObject;
import enclave.encare.encare.modelResponse.AppointmentResponse;
import enclave.encare.encare.service.AccountService;
import enclave.encare.encare.service.AppointmentService;
import enclave.encare.encare.service.FeedbackService;
import enclave.encare.encare.service.UserService;
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

    @PostMapping("/update")
    public ResponseEntity<ResponseObject> update(@Valid @RequestBody InformationForm informationForm){
        informationForm.setAccountId(getAccountId());
        accountService.updateInformation(informationForm);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject(200, "Đã cập nhập thông tin", "")
        );
    }

    @PostMapping("/newAppointment")
    public ResponseEntity<ResponseObject> newAppointment(@Valid @RequestBody AppointmentForm appointmentForm){
        appointmentForm.setAccountUserId(getAccountId());
        if (appointmentService.newAppointment(appointmentForm)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Đặt lịch thành công", "")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Thời gian bạn chọn đã được đặt lịch ", "")
        );
    }

    @PostMapping("/feedback")
    public ResponseEntity<ResponseObject> feedback(@Valid @RequestBody FeedbackForm feedbackForm){
        feedbackForm.setAccountUserId(getAccountId());
        if(feedbackService.newFeedback(feedbackForm)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200,"Đánh giá thành công","")
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject(200, "Đánh giá thất bại","Mục này đã được đánh giá")
        );
    }

    @GetMapping("/history")
    public ResponseEntity<ResponseObject> historyAppointment(@RequestParam(required = false, name = "page", defaultValue = "0") int page){
        long accountId = getAccountId();
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"Lịch sử khám bệnh", appointmentService.historyAppointmentUser(accountId, page))
        );
    }

    @GetMapping("/cancel")
    public ResponseEntity<ResponseObject> cancelAppointment(@RequestParam(required = true, name = "appointmentId") long appoinmentId){
        if (appointmentService.cancelAppointment(getAccountId(), appoinmentId)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Đã hủy lịch hẹn", "")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Hủy không thành công", "Không tồn tại lịch khám này")
        );
    }

    @PostMapping("/uploadAvatar")
    public ResponseEntity<ResponseObject> uploadAvatar(@Valid @ModelAttribute("imageForm") ImageForm imageForm){
        imageForm.setAccountId(getAccountId());
        if (imageForm.getFile().isEmpty()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400,"Bạn chưa chọn ảnh","")
            );
        }
        userService.uploadAvatar(imageForm);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"Đã cập nhập avatar","")
        );
    }

    @PostMapping("/newPassword")
    public ResponseEntity<ResponseObject> newPassowrd(@Valid @RequestBody NewPasswordForm newPasswordForm){
        newPasswordForm.setAccountId(getAccountId());
        if(accountService.updatePassword(newPasswordForm)){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200,"Đã cập nhập mập khẩu","")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400,"Không thể cập nhập mật khẩu","Mật khẩu cũ sai")
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
