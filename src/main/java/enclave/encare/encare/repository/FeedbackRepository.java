package enclave.encare.encare.repository;

import enclave.encare.encare.model.Feedback;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FeedbackRepository extends JpaRepository<Feedback, Long> {
    Feedback findByFeedbackId(long feedbackId);
}