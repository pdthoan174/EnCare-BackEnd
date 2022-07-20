package enclave.encare.encare.service.impl;

import enclave.encare.encare.form.RegisterFormUser;
import enclave.encare.encare.model.Account;
import enclave.encare.encare.model.User;
import enclave.encare.encare.modelResponse.UserResponse;
import enclave.encare.encare.repository.UserRepository;
import enclave.encare.encare.service.AccountService;
import enclave.encare.encare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountService accountService;

    @Override
    public UserResponse findById(long id) {
        return transformData(userRepository.findByUserId(id));
    }

    @Override
    public boolean register(RegisterFormUser registerFormUser) {
        long id = accountService.registerUser(registerFormUser);
        if (id != 0){
            Account account = new Account();
            account.setAccountId(id);

            User user = new User();
            user.setAccount(account);

            userRepository.save(user);
            return true;
        }
        return false;
    }

    @Override
    public List<UserResponse> findAll() {
        return transformData(userRepository.findAll());
    }

    private UserResponse transformData(User user){
        UserResponse userResponse = new UserResponse(
                user.getUserId(),
                accountService.findById(user.getAccount().getAccountId())
        );

        return userResponse;
    }

    private List<UserResponse> transformData(List<User> users){
        List<UserResponse> userResponseList = new ArrayList<>();
        users.forEach(user -> userResponseList.add(transformData(user)));
        return  userResponseList;
    }
}
