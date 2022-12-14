package enclave.encare.encare.repository;

import enclave.encare.encare.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {
    Status findByStatusId(long statusId);
}