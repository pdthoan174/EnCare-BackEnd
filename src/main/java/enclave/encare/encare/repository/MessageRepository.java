package enclave.encare.encare.repository;

import enclave.encare.encare.model.Message;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MessageRepository extends JpaRepository<Message, Long> {
    Message findByMessageId(long messageId);
    @Query("select m from Message m where m.channel.channelId = ?1 order by m.createDate desc ")
    List<Message> listMessageOfChannel(long channelId, Pageable pageable);
}