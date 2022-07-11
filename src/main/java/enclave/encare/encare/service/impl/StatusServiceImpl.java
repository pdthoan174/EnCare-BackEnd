package enclave.encare.encare.service.impl;

import enclave.encare.encare.model.Status;
import enclave.encare.encare.modelResponse.StatusResponse;
import enclave.encare.encare.repository.StatusRepository;
import enclave.encare.encare.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    StatusRepository statusRepository;

    @Override
    public StatusResponse findById(long id) {
        try{
            Status status = statusRepository.findByStatusId(id);
            if (status!=null){
                return transformData(statusRepository.findByStatusId(id));
            }
            return null;
        } catch (Exception e){
            return null;
        }
    }

    private StatusResponse transformData(Status status){
        StatusResponse statusResponse = new StatusResponse();

        statusResponse.setStatusId(status.getStatusId());
        statusResponse.setName(status.getName());
        statusResponse.setDescription(status.getDescription());

        return statusResponse;
    }
}
