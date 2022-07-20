package enclave.encare.encare.repository;

import enclave.encare.encare.model.Hospital;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HospitalRepository extends JpaRepository<Hospital, Long> {
    Hospital findByHospitalId(long id);
}