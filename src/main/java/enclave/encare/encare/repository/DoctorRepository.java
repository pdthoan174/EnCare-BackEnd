package enclave.encare.encare.repository;

import enclave.encare.encare.model.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findByDoctorId(long id);

    Doctor findByAccount_AccountId(long id);

    @Query("SELECT d from Doctor d WHERE d.account.name like %?1% ")
    List<Doctor> findByAccount_Name(String name);

    List<Doctor> findAllByAccountExistsOrderByDoctorIdDesc(Pageable pageable);

    List<Doctor> findDoctorByHospitalOrderByRatingDesc(Hospital hospital);

    List<Doctor> findDoctorByHospitalOrderByRatingDesc(Hospital hospital, Pageable pageable);

    List<Doctor> findDoctorByCategoryOrderByRatingDesc(Category category);

    List<Doctor> findDoctorByCategoryOrderByRatingDesc(Category category, Pageable pageable);
}