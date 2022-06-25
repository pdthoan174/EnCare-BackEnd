package enclave.encare.encare.repository;

import enclave.encare.encare.model.Category;
import enclave.encare.encare.model.Doctor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findByDoctorId(long id);
    @Query("select d from Doctor d where d.category.categoryId = ?1 and d.rating >= ?2 order by d.rating desc ")
    List<Doctor> findDoctorByCategoryAndRatingDesc(long categoryId, float rating, Pageable pageable);
    @Query("select d from Doctor d where d.account.name like %?1% order by d.rating desc ")
    List<Doctor> findDoctorByName(String name, Pageable pageable);

}