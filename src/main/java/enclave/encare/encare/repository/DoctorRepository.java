package enclave.encare.encare.repository;

import enclave.encare.encare.model.Category;
import enclave.encare.encare.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findByDoctorId(long id);
    List<Doctor> findDoctorByCategory(Category category);
}