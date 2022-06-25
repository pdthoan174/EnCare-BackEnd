package enclave.encare.encare.repository;

import enclave.encare.encare.form.AppointmentForm;
import enclave.encare.encare.model.Appointment;
import enclave.encare.encare.model.Doctor;
import enclave.encare.encare.model.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findByAppointmentId(long appointmentId);
    List<Appointment> findByDoctorAndTimeAndDayEquals(Doctor doctor, int time, Date date);
    @Query("select a.doctor from Appointment a where a.appointmentId = ?1")
    Doctor findDoctorByAppointmentId(long appointmentId);
    List<Appointment> findByUser(User user, Pageable pageable);
    List<Appointment> findByDoctorAndDay(Doctor doctor, Date date);
    Appointment findByAppointmentIdAndUser(long appointmentId, User user);
}