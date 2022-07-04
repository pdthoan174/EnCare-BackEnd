package enclave.encare.encare.repository;

import enclave.encare.encare.form.AppointmentForm;
import enclave.encare.encare.model.Appointment;
import enclave.encare.encare.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {

    Appointment findByAppointmentId(long appointmentId);
    Appointment findByUser_Account_Phone(String phone);
    List<Appointment> findByTimeAndDay(int time, Date date);
    List<Appointment> findByTimeAndDayEquals(int time, Date date);
    List<Appointment> findByDoctorDoctorId(long doctorId);
    List<Appointment> findByDoctor_Hospital_HospitalId(long hospitalId);
    List<Appointment> findByStatusStatusId(long statusId);
    @Query("SELECT ap from Appointment ap WHERE ap.user.account.phone like %?1% ")
    List<Appointment> findByUser_Account_PhoneContains(String phone);
}