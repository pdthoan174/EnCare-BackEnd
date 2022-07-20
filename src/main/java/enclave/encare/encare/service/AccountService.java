package enclave.encare.encare.service;

import enclave.encare.encare.form.InformationForm;
import enclave.encare.encare.form.RegisterFormDoctor;
import enclave.encare.encare.form.RegisterFormUser;
import enclave.encare.encare.modelResponse.AccountResponse;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

public interface AccountService {
    public UserDetails getUserDetailById(long id);
    long registerUser(RegisterFormUser registerFormUser);
    long registerDoctor(RegisterFormDoctor registerFormDoctor);
    boolean findByPhone(String phone);
    boolean updateInformation(InformationForm informationForm);
    AccountResponse findById(long accountId);
    List<AccountResponse> findAll();
}
