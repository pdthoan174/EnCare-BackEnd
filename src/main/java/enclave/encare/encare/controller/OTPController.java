package enclave.encare.encare.controller;

import enclave.encare.encare.config.RegexConfig;
import enclave.encare.encare.form.NewPasswordFormForget;
import enclave.encare.encare.form.OTPForm;
import enclave.encare.encare.form.PhoneForm;
import enclave.encare.encare.model.ResponseObject;
import enclave.encare.encare.service.AccountService;
import enclave.encare.encare.service.SmsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/otp")
public class OTPController {
    @Autowired
    SmsService smsService;

    @Autowired
    AccountService accountService;

    @PostMapping("/send")
    public ResponseEntity<ResponseObject> sendOtp(@Valid @RequestBody PhoneForm phoneForm){
        if (!phoneForm.getPhone().matches(RegexConfig.phone)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400,"Send OTP fail", "Phone number is not in the correct format")
            );
        }
        if (smsService.sendMessage(phoneForm.getPhone())){
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Send OTP success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Send OTP fail", "Incorrect phone number")
        );
    }

    @PostMapping("/confirm")
    public ResponseEntity<ResponseObject> confirmOtp(@Valid @RequestBody OTPForm otpForm){
        if (!otpForm.getPhone().matches(RegexConfig.phone)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400,"Confirm OTP fail", "Phone number is not in the correct format")
            );
        }
        if (!otpForm.getOtp().matches(RegexConfig.otp)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400,"Confirm OTP fail", "OTP is not in the correct format")
            );
        }
        otpForm.setPhone("+84"+Long.parseLong(otpForm.getPhone()));
        if (accountService.confirmOTP(otpForm)){
//            accountService.deleteOTP(otpForm.getPhone());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200,"Confirm OTP success", "")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
            new ResponseObject(400,"Confirm OTP fail", "OTP is wrong or phone is wrong")
        );
    }

    @PostMapping("/newPassword")
    public ResponseEntity<ResponseObject> newPassword(@Valid @RequestBody NewPasswordFormForget newPasswordFormForget){
        if (!newPasswordFormForget.getPhone().matches(RegexConfig.phone)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400,"Confirm OTP fail", "Phone number is not in the correct format")
            );
        }
        if (!newPasswordFormForget.getOtp().matches(RegexConfig.otp)){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400,"Confirm OTP fail", "OTP is not in the correct format")
            );
        }
        newPasswordFormForget.setPhone("+84"+Long.parseLong(newPasswordFormForget.getPhone()));
        if (accountService.confirmOTP(new OTPForm(newPasswordFormForget.getOtp(), newPasswordFormForget.getPhone()))){
            accountService.newPassowrd(newPasswordFormForget);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200,"Change password success", "")
            );
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400,"Confirm OTP fail", "OTP is wrong or phone number is wrong")
            );
        }
    }
}
