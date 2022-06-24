package enclave.encare.encare.repository;

import enclave.encare.encare.model.Channel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Channel findByChannelId(long channelId);
}