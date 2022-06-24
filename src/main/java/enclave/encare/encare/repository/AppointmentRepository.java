package enclave.encare.encare.repository;

import enclave.encare.encare.model.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    Appointment findByAppointmentId(long appointmentId);
}