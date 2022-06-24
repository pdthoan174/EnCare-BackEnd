package enclave.encare.encare.service.impl;

import enclave.encare.encare.model.Feedback;
import enclave.encare.encare.modelResponse.FeedbackResponse;
import enclave.encare.encare.repository.FeedbackRepository;
import enclave.encare.encare.service.FeedbackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Override
    public FeedbackResponse findById(long id) {
        return transformData(feedbackRepository.findByFeedbackId(id));
    }

    private FeedbackResponse transformData(Feedback feedback){
        FeedbackResponse feedbackResponse = new FeedbackResponse();

        feedbackResponse.setFeedbackId(feedback.getFeedbackId());
        feedbackResponse.setComment(feedback.getComment());
        feedbackResponse.setRating(feedback.getRating());

        return feedbackResponse;
    }
}
