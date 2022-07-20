package enclave.encare.encare.repository;

import enclave.encare.encare.model.Account;
import enclave.encare.encare.model.Channel;
import enclave.encare.encare.model.Doctor;
import enclave.encare.encare.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChannelRepository extends JpaRepository<Channel, Long> {
    Channel findByChannelId(long channelId);

    @Query("select c from Channel c where c.doctor.accountId = ?1 and c.user.accountId = ?2")
    Channel findChannel(long accountDoctorId, long accountUserId);
}