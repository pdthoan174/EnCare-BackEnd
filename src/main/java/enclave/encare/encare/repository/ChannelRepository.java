package enclave.encare.encare.repository;

import enclave.encare.encare.model.Account;
import enclave.encare.encare.model.Channel;
import enclave.encare.encare.model.Doctor;
import enclave.encare.encare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Channel findByChannelId(long channelId);
    Channel findByDoctorAndUser(Account doctor, Account user);
}