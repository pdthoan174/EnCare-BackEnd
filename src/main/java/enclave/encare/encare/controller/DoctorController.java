package enclave.encare.encare.controller;

import enclave.encare.encare.config.TimeConfig;
import enclave.encare.encare.form.DescriptionAppointmentForm;
import enclave.encare.encare.form.DoctorInformationForm;
import enclave.encare.encare.model.ResponseObject;
import enclave.encare.encare.modelResponse.AppointmentResponse;
import enclave.encare.encare.modelResponse.CategoryResponse;
import enclave.encare.encare.modelResponse.DoctorResponse;
import enclave.encare.encare.modelResponse.HospitalResponse;
import enclave.encare.encare.service.AppointmentService;
import enclave.encare.encare.service.CategoryService;
import enclave.encare.encare.service.DoctorService;
import enclave.encare.encare.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    DoctorService doctorService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    HospitalService hospitalService;

    @Autowired
    AppointmentService appointmentService;


    @GetMapping("/")
    public ResponseEntity<ResponseObject> listDoctor() {
        List<DoctorResponse> doctorResponseList = doctorService.listDoctor();
        if (doctorResponseList == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Get to fail", "Cant load this page")
            );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "List Doctor", doctorResponseList)
        );
    }

    @PostMapping("/update")
    public ResponseEntity<ResponseObject> updateDoctor(@RequestBody DoctorInformationForm doctorInformationForm) throws ParseException {
        boolean update = doctorService.updateInfor(doctorInformationForm);
        if (!update)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Update to fail", "Cant update doctor")
            );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Update to success", "update success for doctor " + doctorInformationForm.getDoctorId())
        );
    }

    //    @GetMapping("/page={page}")
//    public ResponseEntity<ResponseObject> listDoctor(@PathVariable("page") int page){
//        List<DoctorResponse> doctorResponseList = doctorService.listDoctor(page);
//        if (doctorResponseList==null)
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
//                    new ResponseObject(400, "Get to fail","Cant load this page" )
//            );
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(200, "List Doctor by page "+page,doctorResponseList )
//        );
//    }
    @GetMapping("/doctorId={doctorId}")
    public ResponseEntity<ResponseObject> doctorById(@PathVariable("doctorId") int doctorId) {
        DoctorResponse doctorResponse = doctorService.findById(doctorId);
        if (doctorResponse == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Doctor detail", "DoctorId is not found !")
            );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "List Category", doctorResponse)
        );
    }

    @GetMapping("/doctorName={doctorName}")
    public ResponseEntity<ResponseObject> doctorById(@PathVariable("doctorName") String doctorName) {
        DoctorResponse doctorResponse = doctorService.findByName(doctorName);
        if (doctorResponse == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Doctor detail", "DoctorId is not found !")
            );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "List Category", doctorResponse)
        );
    }

    @GetMapping("/hospitalId={hospitalId}")
    public ResponseEntity<ResponseObject> listDoctorByHospitalId(@PathVariable("hospitalId") int hospitalId) {
        HospitalResponse hospitalResponse = hospitalService.findById(hospitalId);
        if (hospitalResponse == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "List doctor of hospital", "Hospital id invalid !")
            );
        List<DoctorResponse> doctorResponseList = doctorService.listDoctorOfHospital(hospitalId);
        if (doctorResponseList == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "List doctor of hospital", "Hospital has not doctor ")
            );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "List doctor of hospital", doctorResponseList)
        );
    }

    //    @GetMapping("/hospitalId={hospitalId}&page={page}")
//    public ResponseEntity<ResponseObject> listDoctorByHospitalId(@PathVariable("hospitalId") int hospitalId,@PathVariable("page") int page){
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(200, "List Category", doctorService.listDoctorOfHospital(hospitalId,page))
//        );
//    }
    @GetMapping("/categoryId={categoryId}")
    public ResponseEntity<ResponseObject> listDoctorByCategoryId(@PathVariable("categoryId") int categoryId) {
        CategoryResponse categoryResponse = categoryService.findById(categoryId);
        String description = "List doctor of category";
        if (categoryResponse == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, description, "Category invalid")
            );
        List<DoctorResponse> doctorResponseList = doctorService.listDoctorOfCategory(categoryId);
        if (doctorResponseList == null || doctorResponseList.size() < 1)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, description, "Category has not doctor")
            );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, description, doctorResponseList)
        );
    }

    //    @GetMapping("/category/Id={categoryId}&page={page}")
//    public ResponseEntity<ResponseObject> listDoctorByCategoryId(@PathVariable("categoryId") int categoryId,@PathVariable("page") int page){
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(200, "List Category", doctorService.listDoctorOfCategory(categoryId,page))
//        );
//    }
    @GetMapping("/appointment/doctorId={doctorId}")
    public ResponseEntity<ResponseObject> appointmentByDoctorId(@PathVariable("doctorId") long doctorId) {

        if (doctorService.findById(doctorId)!=null){
            List<AppointmentResponse> appointmentResponseList = appointmentService.findByDoctorId(doctorId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Appointment by doctorId", appointmentResponseList)
            );
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "No has appointment by doctorId", "Failed"));
    }
    @GetMapping("/confirm/appointmentId={appointmentId}")
    public ResponseEntity<ResponseObject> confirmAppointment(@PathVariable("appointmentId") long appointmentId) {

        AppointmentResponse appointmentResponse = appointmentService.findById(appointmentId);
//        List<AppointmentResponse> appointmentResponseList = appointmentService.
        boolean confirm = false;

        if (appointmentResponse != null && appointmentResponse.getStatusResponse().getStatusId() == 2) {
            List<AppointmentResponse> appointmentResponseList =appointmentService.findByDoctorId(appointmentResponse.getDoctorResponse().getDoctorId());
            for (AppointmentResponse a:appointmentResponseList){
                int newAppointmentTime = appointmentResponse.getTime();
                String newAppointmentDate = appointmentResponse.getDay();
                if (a.getAppointmentId()!=appointmentResponse.getAppointmentId()
                        &&a.getDay().equals(newAppointmentDate)
                        &&a.getTime()==newAppointmentTime
                        && (a.getStatusResponse().getStatusId() != 1
                )           || a.getStatusResponse().getStatusId() != 5)
                {
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                            new ResponseObject(400, "Confirm appointment", "Time is invalid"));
                }
            }
            confirm = doctorService.changeStatusAppointment(appointmentId, 3);
        }
        if (confirm) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Confirm appointment", "Success")
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Confirm appointment", "Failed"));
    }

    @GetMapping("/cancel/appointmentId={appointmentId}")
    public ResponseEntity<ResponseObject> cancelAppointment(@PathVariable("appointmentId") long appointmentId) {
        AppointmentResponse appointmentResponse = appointmentService.findById(appointmentId);
        boolean confirm = false;
        if (appointmentResponse != null && appointmentResponse.getStatusResponse().getStatusId() <3) {
            confirm = doctorService.changeStatusAppointment(appointmentId, 1);
        }
        if (confirm)
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Cancel appointment", "Success")
            );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Cancel appointment", "Failed"));
    }

    @GetMapping("/done/appointmentId={appointmentId}")
    public ResponseEntity<ResponseObject> doneAppointment(@PathVariable("appointmentId") long appointmentId) {
        AppointmentResponse appointmentResponse = appointmentService.findById(appointmentId);
        boolean confirm = false;
        if (appointmentResponse != null && appointmentResponse.getStatusResponse().getStatusId() > 2) {
            confirm = doctorService.changeStatusAppointment(appointmentId, 4);
        }
        if (confirm)
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Cancel appointment", "Success")
            );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Cancel appointment", "Failed"));
    }

    @GetMapping("/re-examination/appointmentId={appointmentId}")
    public ResponseEntity<ResponseObject> reExaminationAppointment(@PathVariable("appointmentId") long appointmentId) {
        AppointmentResponse appointmentResponse = appointmentService.findById(appointmentId);
        boolean confirm = false;
        if (appointmentResponse != null && appointmentResponse.getStatusResponse().getStatusId() > 3) {
            confirm = doctorService.changeStatusAppointment(appointmentId, 5);
        }
        if (confirm)
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Re-examination appointment", "Success")
            );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Re-examination appointment", "Failed"));
    }

    @PostMapping("/descriptionAppointment")
    public ResponseEntity<ResponseObject> descriptionAppointment(@RequestBody DescriptionAppointmentForm descriptionAppointmentForm) {
        AppointmentResponse appointmentResponse = appointmentService.findById(descriptionAppointmentForm.getId());
        boolean confirm = false;
        if (appointmentResponse!=null && appointmentResponse.getStatusResponse().getStatusId()>3){
            confirm = appointmentService.setDescription(descriptionAppointmentForm.getId(),descriptionAppointmentForm.getDescription());
        }
        if (confirm)
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Set description for appointment", "Success")
            );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Set description for appointment", "Failed"));
    }


}
