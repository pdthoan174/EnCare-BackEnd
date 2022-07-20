package enclave.encare.encare.repository;

//<<<<<<< HEAD
import enclave.encare.encare.model.Account;
import enclave.encare.encare.model.Category;
import enclave.encare.encare.model.Doctor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
//=======
import enclave.encare.encare.model.*;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
//>>>>>>> doctor
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Doctor findByDoctorId(long id);
//<<<<<<< HEAD
    Doctor findByAccount(Account account);
    @Query("select d from Doctor d where d.account.accountId = ?1")
    Doctor findDoctorByAccountId(long accountId);
    @Query("select d from Doctor d where d.category.categoryId = ?1 and d.rating >= ?2 order by d.rating desc ")
    List<Doctor> findDoctorByCategoryAndRatingDesc(long categoryId, float rating, Pageable pageable);
    @Query("select d from Doctor d where d.account.name like %?1% order by d.rating desc ")
    List<Doctor> findDoctorByName(String name, Pageable pageable);

//=======

    Doctor findByAccount_AccountId(long id);

    @Query("SELECT d from Doctor d WHERE d.account.name like %?1% ")
    List<Doctor> findByAccount_Name(String name);

    List<Doctor> findAllByAccountExistsOrderByDoctorIdDesc(Pageable pageable);

    List<Doctor> findDoctorByHospitalOrderByRatingDesc(Hospital hospital);

    List<Doctor> findDoctorByHospitalOrderByRatingDesc(Hospital hospital, Pageable pageable);

    List<Doctor> findDoctorByCategoryOrderByRatingDesc(Category category);

    List<Doctor> findDoctorByCategoryOrderByRatingDesc(Category category, Pageable pageable);
//>>>>>>> doctor
}