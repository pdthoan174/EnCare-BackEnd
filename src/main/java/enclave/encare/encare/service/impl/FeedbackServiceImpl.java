package enclave.encare.encare.service.impl;

import enclave.encare.encare.form.FeedbackForm;
import enclave.encare.encare.model.Appointment;
import enclave.encare.encare.model.Feedback;
import enclave.encare.encare.model.User;
import enclave.encare.encare.modelResponse.FeedbackResponse;
import enclave.encare.encare.modelResponse.UserResponse;
import enclave.encare.encare.repository.FeedbackRepository;
import enclave.encare.encare.service.AppointmentService;
import enclave.encare.encare.service.DoctorService;
import enclave.encare.encare.service.FeedbackService;
import enclave.encare.encare.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FeedbackServiceImpl implements FeedbackService {

    @Autowired
    FeedbackRepository feedbackRepository;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    DoctorService doctorService;

    @Autowired
    UserService userService;

    @Override
    public FeedbackResponse findById(long id) {
        return transformData(feedbackRepository.findByFeedbackId(id));
    }

    @Override
    public boolean newFeedback(FeedbackForm feedbackForm) {
        if (checkFeedback(feedbackForm.getAppointmentId())){
            long userId = userService.findUserIdByAccountId(feedbackForm.getAccountUserId());
            User user = new User(userId);
            Appointment appointment = new Appointment(feedbackForm.getAppointmentId());

            Feedback feedback = new Feedback();
            feedback.setRating(Math.round(feedbackForm.getRating()));
            feedback.setComment(feedbackForm.getComment());
            feedback.setUser(user);
            feedback.setAppointment(appointment);
            feedbackRepository.save(feedback);

            doctorService.updateRating(appointment.getAppointmentId(), feedbackForm.getRating());
            return true;
        }
        return false;
    }

    private boolean checkFeedback(long appointmentId){
        Appointment appointment = new Appointment(appointmentId);
        Feedback feedback = feedbackRepository.findByAppointment(appointment);
        if (feedback==null){
            return true;
        }
        return false;
    }

    private FeedbackResponse transformData(Feedback feedback){
        FeedbackResponse feedbackResponse = new FeedbackResponse();

        feedbackResponse.setFeedbackId(feedback.getFeedbackId());
        feedbackResponse.setComment(feedback.getComment());
        feedbackResponse.setRating(feedback.getRating());

        return feedbackResponse;
    }
}
