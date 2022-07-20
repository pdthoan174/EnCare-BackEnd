package enclave.encare.encare.service;

import enclave.encare.encare.form.ImageForm;
import enclave.encare.encare.form.RegisterFormUser;
import enclave.encare.encare.modelResponse.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserService {
    UserResponse findById(long id);
    long findUserIdByAccountId(long accountId);
    UserResponse findUserByAccountId(long accountId);
    boolean register(RegisterFormUser registerFormUser);
<<<<<<< HEAD
    List<UserResponse> findAll();
=======
    void uploadAvatar(ImageForm imageForm);
>>>>>>> 82c86b93a95a2cef2ce9f9ddbacedceaaf7d22cc
}
