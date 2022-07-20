package enclave.encare.encare.repository;

import enclave.encare.encare.model.Account;
import enclave.encare.encare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(long userId);
    User findByAccount(Account account);
    @Query("select u from User u where u.account.accountId = ?1")
    User findUserByAccountId(long accountId);
}