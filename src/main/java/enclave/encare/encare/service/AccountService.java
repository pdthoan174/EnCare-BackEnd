package enclave.encare.encare.service;

import org.springframework.security.core.userdetails.UserDetails;

public interface AccountService {
    public UserDetails getUserDetailById(long id);
}
