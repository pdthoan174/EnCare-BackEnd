package enclave.encare.encare.repository;

import enclave.encare.encare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserId(long userId);
}