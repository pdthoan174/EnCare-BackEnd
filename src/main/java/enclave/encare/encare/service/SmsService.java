package enclave.encare.encare.service;

import com.twilio.rest.api.v2010.account.Message;
import com.twilio.rest.api.v2010.account.MessageCreator;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.trial_phone}")
    private String trialPhone;

    @Autowired
    AccountService accountService;

    public boolean sendMessage(String phone){
        try {
            String phoneNumber = "+84"+Long.parseLong(phone);
            if (!accountService.findByPhone(phoneNumber)){
                int number = (int) (Math.random()*(999999-100000+1)+100000);
                PhoneNumber from = new PhoneNumber(trialPhone);
                PhoneNumber to = new PhoneNumber(phoneNumber);
                String otp = number+"";
                accountService.newOTP(phoneNumber, otp);
                MessageCreator creator = Message.creator(to, from, "OTP: "+otp);
                creator.create();
                return true;
            }
            return false;
        } catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }
}
