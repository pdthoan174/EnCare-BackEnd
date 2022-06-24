package enclave.encare.encare.repository;

import enclave.encare.encare.model.Appointment;
import enclave.encare.encare.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Feedback findByFeedbackId(long feedbackId);
    Feedback findByAppointment(Appointment appointment);
}