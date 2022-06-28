package enclave.encare.encare.controller;

import enclave.encare.encare.form.AppointmentForm;
import enclave.encare.encare.form.FeedbackForm;
import enclave.encare.encare.form.ImageForm;
import enclave.encare.encare.form.InformationForm;
import enclave.encare.encare.jwt.JwtTokenProvider;
import enclave.encare.encare.model.ResponseObject;
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

@RestController
@RequestMapping("/user")
public class UserController {

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


    @RequestMapping("/abc")
    public String index(){
        return "index user";
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseObject> update(@RequestBody InformationForm informationForm){
        informationForm.setAccountId(getAccountId());
        accountService.updateInformation(informationForm);
        return ResponseEntity.status(HttpStatus.OK).body(
            new ResponseObject(200, "Đã cập nhập thông tin", "")
        );
    }

    @PostMapping("/newAppointment")
    public ResponseEntity<ResponseObject> newAppointment(@RequestBody AppointmentForm appointmentForm){
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
    public ResponseEntity<ResponseObject> feedback(@RequestBody FeedbackForm feedbackForm){
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
    public ResponseEntity<ResponseObject> uploadAvatar(@ModelAttribute("imageForm") ImageForm imageForm){
        imageForm.setAccountId(getAccountId());
        userService.uploadAvatar(imageForm);
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200,"Đã cập nhập avatar","")
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
