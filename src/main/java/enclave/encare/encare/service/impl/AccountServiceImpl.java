package enclave.encare.encare.service.impl;

import enclave.encare.encare.model.Account;
import enclave.encare.encare.repository.AccountRepository;
import enclave.encare.encare.service.AccountService;
import enclave.encare.encare.until.CustomUserDetail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements AccountService, UserDetailsService {

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
}
