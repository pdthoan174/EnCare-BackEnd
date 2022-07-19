package enclave.encare.encare.service.impl;

import enclave.encare.encare.config.TimeConfig;
import enclave.encare.encare.form.*;
import enclave.encare.encare.model.Account;
import enclave.encare.encare.modelResponse.AccountResponse;
import enclave.encare.encare.repository.AccountRepository;
import enclave.encare.encare.service.AccountService;
import enclave.encare.encare.until.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByPhone(phone);
        if (account==null){
            throw new UsernameNotFoundException(phone);
        }
        return new CustomUserDetail(account);
    }


    @Override
    public UserDetails getUserDetailById(long id) {
        Account account = accountRepository.findByAccountId(id);
        return new CustomUserDetail(account);
    }

    // return id of account
    @Override
    public long registerUser(RegisterFormUser registerFormUser) {
        if (findByPhone("+84"+Long.parseLong(registerFormUser.getPhone().trim()))){
            Account account = new Account();

            account.setPhone("+84"+Long.parseLong(registerFormUser.getPhone().trim()));
            account.setPassword(passwordEncoder.encode(registerFormUser.getPassword()));
            account.setRole("PATIENT");
            account.setName(registerFormUser.getName().trim());
            account.setCreateDate(new Date());

            return accountRepository.save(account).getAccountId();
        }
        return 0;
    }

    @Override
    public long registerDoctor(RegisterFormDoctor registerFormDoctor) {
        if (findByPhone(registerFormDoctor.getPhone())){
            Account account = new Account();

            account.setPhone("+84"+Long.parseLong(registerFormDoctor.getPhone().trim()));
            account.setPassword(passwordEncoder.encode(registerFormDoctor.getPassword()));
            account.setRole("DOCTOR");
            account.setName(registerFormDoctor.getName().trim());
            account.setBirthday(TimeConfig.getDate(registerFormDoctor.getBirthDay()));
            account.setCreateDate(new Date());
            account.setDescription(registerFormDoctor.getDescription().trim());

            return accountRepository.save(account).getAccountId();
        }
        return 0;
    }

    // check for existence phone
    @Override
    public boolean findByPhone(String phone) {
        try {
            Account account = accountRepository.findByPhone(phone);
            if (account == null){
                return true;
            }
            return false;
        } catch (Exception e){
            return true;
        }
    }

    @Override
    public boolean updateInformation(InformationForm informationForm) {
        Account account = accountRepository.findByAccountId(informationForm.getAccountId());
        if (!informationForm.getBirthDay().isBlank()){
            Date date = TimeConfig.getDate(informationForm.getBirthDay());
            Date now = new Date();
            if (!date.before(now)) {
                return false;
            }
            account.setBirthday(TimeConfig.getDate(informationForm.getBirthDay()));
        }
        account.setName(informationForm.getName().trim());
        account.setDescription(informationForm.getDescription().trim());

        accountRepository.save(account);

        return true;
    }

    @Override
    public AccountResponse findById(long accountId) {
        try {
            Account account = accountRepository.findByAccountId(accountId);
            if (account!=null){
                return transformData(account);
            }
            return null;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public boolean updatePassword(NewPasswordForm newPasswordForm) {
        if (checkPass(newPasswordForm.getOldPassword(), newPasswordForm.getAccountId())){
            Account account = accountRepository.findByAccountId(newPasswordForm.getAccountId());
            account.setPassword(passwordEncoder.encode(newPasswordForm.getNewPassword()));
            accountRepository.save(account);
            return true;
        }
        return false;
    }

    @Override
    public void newOTP(String phone, String otp) {
        try {
            Account account = accountRepository.findByPhone(phone);
            if (account!=null){
                account.setOtpCode(otp);
                accountRepository.save(account);
            }
        } catch (Exception e){
        }
    }

    @Override
    public boolean confirmOTP(OTPForm otpForm) {
        try {
            Account account = accountRepository.findByPhone(otpForm.getPhone());
            if (account!=null){
                return otpForm.getOtp().equals(account.getOtpCode());
            }
            return false;
        } catch (Exception e){
            return false;
        }
    }

    @Override
    public boolean newPassowrd(NewPasswordFormForget newPasswordFormForget) {
        try {
            Account account = accountRepository.findByPhone(newPasswordFormForget.getPhone());
            if (account!=null){
                if (newPasswordFormForget.getOtp().equals(account.getOtpCode())){
                    account.setOtpCode(null);
                    account.setPassword(passwordEncoder.encode(newPasswordFormForget.getPassword()));
                    accountRepository.save(account);
                    return true;
                }
                return false;
            }
            return false;
        } catch (Exception e){
            return false;
        }
    }

    private boolean checkPass(String oldPass, long accountId){
        Account account = accountRepository.findByAccountId(accountId);
        return BCrypt.checkpw(oldPass, account.getPassword());
    }

    private AccountResponse transformData(Account account){
        AccountResponse accountResponse = new AccountResponse();

        accountResponse.setAccountId(account.getAccountId());
        accountResponse.setPhone(account.getPhone());
        accountResponse.setRole(account.getRole());
        accountResponse.setName(account.getName());
        accountResponse.setAvatar(account.getAvatar());
        accountResponse.setDescription(account.getDescription());
        if (account.getBirthday()!=null){
            accountResponse.setBirthday(TimeConfig.getTime(account.getBirthday()));
        }
        accountResponse.setCreateDate(TimeConfig.getTime(account.getCreateDate()));

        return accountResponse;
    }
}
