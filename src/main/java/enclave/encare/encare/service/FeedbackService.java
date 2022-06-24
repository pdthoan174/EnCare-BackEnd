package enclave.encare.encare.service;

import enclave.encare.encare.form.FeedbackForm;
import enclave.encare.encare.modelResponse.FeedbackResponse;

public interface FeedbackService {
    FeedbackResponse findById(long id);
    boolean newFeedback(FeedbackForm feedbackForm);
}
