package enclave.encare.encare.service.impl;

import enclave.encare.encare.form.ImageForm;
import enclave.encare.encare.form.RegisterFormUser;
import enclave.encare.encare.model.Account;
import enclave.encare.encare.model.User;
import enclave.encare.encare.modelResponse.UserResponse;
import enclave.encare.encare.repository.AccountRepository;
import enclave.encare.encare.repository.UserRepository;
import enclave.encare.encare.service.AccountService;
import enclave.encare.encare.service.StorageService;
import enclave.encare.encare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    StorageService storageService;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    AccountService accountService;

    @Override
    public UserResponse findById(long id) {
        try {
            User user = userRepository.findByUserId(id);
            if (user!=null){
                return transformData(userRepository.findByUserId(id));
            }
            return null;
        } catch (Exception e){
            return null;
        }
    }

    @Override
    public long findUserIdByAccountId(long accountId) {
        return userRepository.findUserByAccountId(accountId).getUserId();
    }

    @Override
    public UserResponse findUserByAccountId(long accountId) {
        return findById(findUserIdByAccountId(accountId));
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
    public void uploadAvatar(ImageForm imageForm) {
        Account account = accountRepository.findByAccountId(imageForm.getAccountId());
        account.setAvatar(storageService.uploadFile(imageForm.getFile()));
        accountRepository.save(account);
    }

    private UserResponse transformData(User user){
        UserResponse userResponse = new UserResponse(
                user.getUserId(),
                accountService.findById(user.getAccount().getAccountId())
        );

        return userResponse;
    }
}
