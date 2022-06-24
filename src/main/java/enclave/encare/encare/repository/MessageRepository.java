package enclave.encare.encare.repository;

import enclave.encare.encare.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Message findByMessageId(long messageId);
}