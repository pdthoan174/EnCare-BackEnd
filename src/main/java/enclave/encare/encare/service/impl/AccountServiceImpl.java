package enclave.encare.encare.service.impl;

import enclave.encare.encare.config.TimeConfig;
import enclave.encare.encare.form.InformationForm;
import enclave.encare.encare.form.RegisterFormDoctor;
import enclave.encare.encare.form.RegisterFormUser;
import enclave.encare.encare.model.Account;
import enclave.encare.encare.modelResponse.AccountResponse;
import enclave.encare.encare.repository.AccountRepository;
import enclave.encare.encare.service.AccountService;
import enclave.encare.encare.until.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        Account account = accountRepository.findAccountByPhone(phone);
        if (account == null) {
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
        if (findByPhone(registerFormUser.getPhone())) {
            Account account = new Account();

            account.setPhone(registerFormUser.getPhone());
            account.setPassword(passwordEncoder.encode(registerFormUser.getPassword()));
            account.setRole("USER");
            account.setName(registerFormUser.getName());
            account.setBirthday(TimeConfig.getDate(registerFormUser.getBirthDay()));
            account.setCreateDate(new Date());

            return accountRepository.save(account).getAccountId();
        }
        return 0;
    }

    @Override
    public long registerDoctor(RegisterFormDoctor registerFormDoctor) {
        if (findByPhone(registerFormDoctor.getPhone())) {
            Account account = new Account();

            account.setPhone(registerFormDoctor.getPhone());
            account.setPassword(passwordEncoder.encode(registerFormDoctor.getPassword()));
            account.setRole("DOCTOR");
            account.setName(registerFormDoctor.getName());
            account.setBirthday(TimeConfig.getDate(registerFormDoctor.getBirthDay()));
            account.setCreateDate(new Date());
            account.setDescription(registerFormDoctor.getDescription());

            return accountRepository.save(account).getAccountId();
        }
        return 0;
    }

    // check for existence phone
    @Override
    public boolean findByPhone(String phone) {
        Account account = accountRepository.findByPhone(phone);
        if (account == null) {
            return true;
        }
        return false;
    }

    @Override
    public boolean updateInformation(InformationForm informationForm) {
        Account account = accountRepository.findByAccountId(informationForm.getAccountId());
        account.setAccountId(informationForm.getAccountId());

        account.setName(informationForm.getName());
        account.setBirthday(TimeConfig.getDate(informationForm.getBirthDay()));
        account.setDescription(informationForm.getDescription());

        accountRepository.save(account);

        return true;
    }

    @Override
    public AccountResponse findById(long accountId) {
        return transformData(accountRepository.findByAccountId(accountId));
    }

    @Override
    public List<AccountResponse> findAll() {
        return transformData(accountRepository.findAll());
    }

    private AccountResponse transformData(Account account) {
        AccountResponse accountResponse = new AccountResponse();

        accountResponse.setAccountId(account.getAccountId());
        accountResponse.setPhone(account.getPhone());
        accountResponse.setRole(account.getRole());
        accountResponse.setName(account.getName());
        accountResponse.setAvatar(account.getAvatar());
        accountResponse.setDescription(account.getDescription());
        Date date = account.getBirthday();
        if (date!=null)
            accountResponse.setBirthday(TimeConfig.getTime(date));
        String createDate = TimeConfig.getTime(account.getCreateDate());
        if (createDate!=null || createDate.length()>0){
            accountResponse.setCreateDate(createDate);
        }


        return accountResponse;
    }

    private List<AccountResponse> transformData(List<Account> accounts) {
        List<AccountResponse> accountResponse =new ArrayList<>();
        accounts.forEach(account -> accountResponse.add(transformData(account)));
        return accountResponse;
    }
}
