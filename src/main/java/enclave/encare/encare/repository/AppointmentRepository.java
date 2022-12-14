package enclave.encare.encare.repository;

import enclave.encare.encare.form.AppointmentForm;
import enclave.encare.encare.model.Appointment;
import enclave.encare.encare.model.Doctor;
//<<<<<<< HEAD
import enclave.encare.encare.model.Status;
import enclave.encare.encare.model.User;
import org.springframework.data.domain.Pageable;
//=======
//>>>>>>> doctor
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Appointment findByAppointmentId(long appointmentId);
//<<<<<<< HEAD
    List<Appointment> findByDoctorAndTimeAndDayEquals(Doctor doctor, int time, Date date);
    @Query("select a.doctor from Appointment a where a.appointmentId = ?1")
    Doctor findDoctorByAppointmentId(long appointmentId);
    List<Appointment> findByUser(User user, Pageable pageable);
    List<Appointment> findByDoctorAndDay(Doctor doctor, Date date);
    Appointment findByAppointmentIdAndUser(long appointmentId, User user);
//=======
    Appointment findByUser_Account_Phone(String phone);
    List<Appointment> findByTimeAndDay(int time, Date date);
    List<Appointment> findByTimeAndDayEquals(int time, Date date);
    List<Appointment> findByDoctorDoctorId(long doctorId);
    List<Appointment> findByDoctor_Hospital_HospitalId(long hospitalId);
    List<Appointment> findByStatusStatusId(long statusId);
//<<<<<<< HEAD
    @Query("SELECT ap from Appointment ap WHERE ap.user.account.phone like %?1% and ap.doctor.account.accountId= ?2")
    List<Appointment> find_by_phone_and_accountId(String phone,Long accountId);
    @Query("SELECT ap from Appointment ap WHERE ap.user.account.name like %?1% and ap.doctor.account.accountId= ?2 ")
    List<Appointment> find_by_name_and_accountId(String name,Long accountId);
    @Query("SELECT ap from Appointment ap WHERE ap.description like %?1% and ap.doctor.account.accountId= ?2 ")
    List<Appointment> find_by_descriptions_and_accountId(String descriptions,Long accountId);
    @Query("SELECT ap from Appointment ap WHERE ap.symptoms like %?1% and ap.doctor.account.accountId= ?2")
    List<Appointment> find_by_symptoms_and_accountId(String symptoms,Long accountId);
    List<Appointment> findByStatusStatusIdAndAndDoctor_Account_AccountIdOrderByCreateDate(Long statusId,Long accountId);
    Appointment findByAppointmentIdAndAndDoctor_Account_AccountIdOrderByCreateDate(Long appointmentId,Long accountId);
//=======
    @Query("SELECT ap from Appointment ap WHERE ap.user.account.phone like %?1% ")
    List<Appointment> findByUser_Account_PhoneContains(String phone);

    List<Appointment> findByUserAndStatus(User user, Status status, Pageable pageable);
}