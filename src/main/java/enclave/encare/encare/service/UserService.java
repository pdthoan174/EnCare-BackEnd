package enclave.encare.encare.service;

import enclave.encare.encare.form.RegisterFormUser;
import enclave.encare.encare.modelResponse.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserService {
    UserResponse findById(long id);
    boolean register(RegisterFormUser registerFormUser);
    List<UserResponse> findAll();
}
