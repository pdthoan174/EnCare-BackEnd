package enclave.encare.encare.service.impl;

import enclave.encare.encare.model.Status;
import enclave.encare.encare.modelResponse.StatusResponse;
import enclave.encare.encare.repository.StatusRepository;
import enclave.encare.encare.service.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    StatusRepository statusRepository;

    @Override
    public StatusResponse findById(long id) {
        Status status = statusRepository.findByStatusId(id);
        if (status==null) return null;
        return transformData(status);
    }

    @Override
    public List<StatusResponse> findAll() {
        List<StatusResponse> statusResponses = new ArrayList<>();
        statusResponses=transformData(statusRepository.findAll());
        return statusResponses;
    }

    private StatusResponse transformData(Status status){
        StatusResponse statusResponse = new StatusResponse();

        statusResponse.setStatusId(status.getStatusId());
        statusResponse.setName(status.getName());
        statusResponse.setDescription(status.getDescription());

        return statusResponse;
    }
    private List<StatusResponse> transformData(List<Status> statusList){
        List<StatusResponse> statusResponses = new ArrayList<>();
        statusList.forEach(status -> statusResponses.add(transformData(status)));
        return statusResponses;
    }
}
