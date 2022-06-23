package enclave.encare.encare.repository;

import enclave.encare.encare.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByAccountId(long accountId);
    Account findAccountByPhone(String phone);
}