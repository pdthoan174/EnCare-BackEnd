package enclave.encare.encare.controller;

import enclave.encare.encare.config.TimeConfig;
import enclave.encare.encare.form.DescriptionAppointmentForm;
import enclave.encare.encare.form.DoctorInformationForm;
import enclave.encare.encare.model.Appointment;
import enclave.encare.encare.model.ResponseObject;
import enclave.encare.encare.model.User;
import enclave.encare.encare.modelResponse.*;
import enclave.encare.encare.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/doctor")
public class DoctorController {

    @Autowired
    DoctorService doctorService;

    @Autowired
    CategoryService categoryService;

    @Autowired
    HospitalService hospitalService;

    @Autowired
    AppointmentService appointmentService;

    @Autowired
    StatusService statusService;


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

    @PutMapping("/")
    public ResponseEntity<ResponseObject> updateDoctor(@RequestBody DoctorInformationForm doctorInformationForm) throws ParseException {
        //handle in updateInfor() :
        String update = doctorService.updateInfor(doctorInformationForm);
        // if when update throw error -> update return name error
        if (!update.toLowerCase().contains("success"))
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Update to fail", update)
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
    public ResponseEntity<ResponseObject> doctorByName(@PathVariable("doctorName") String doctorName) {
        List<DoctorResponse> doctorResponseList = doctorService.findByName(doctorName);
        if (doctorResponseList == null || doctorResponseList.size() < 1)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Doctor is not exist !", null)
            );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Doctor by name", doctorResponseList)
        );
    }

    @GetMapping("/hospitalId={hospitalId}")
    public ResponseEntity<ResponseObject> doctorByHospitalId(@PathVariable("hospitalId") int hospitalId) {
        HospitalResponse hospitalResponse = hospitalService.findById(hospitalId);
        if (hospitalResponse == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Hospital is not exist !", null)
            );
        List<DoctorResponse> doctorResponseList = doctorService.listDoctorOfHospital(hospitalId);
        if (doctorResponseList == null || doctorResponseList.size() < 1)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(200, "Hospital has not doctor ", doctorResponseList)
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
    public ResponseEntity<ResponseObject> doctorByCategoryId(@PathVariable("categoryId") int categoryId) {
        CategoryResponse categoryResponse = categoryService.findById(categoryId);
        if (categoryResponse == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Category is not exist !", null)
            );
        List<DoctorResponse> doctorResponseList = doctorService.listDoctorOfCategory(categoryId);
        if (doctorResponseList == null || doctorResponseList.size() < 1)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(200, "Category has not doctor", doctorResponseList)
            );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "List doctor of category", doctorResponseList)
        );
    }

    //    @GetMapping("/category/Id={categoryId}&page={page}")
//    public ResponseEntity<ResponseObject> listDoctorByCategoryId(@PathVariable("categoryId") int categoryId,@PathVariable("page") int page){
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(200, "List Category", doctorService.listDoctorOfCategory(categoryId,page))
//        );
//    }
    @GetMapping("/appointment")
    public ResponseEntity<ResponseObject> getAppointments() {

        List<AppointmentResponse> appointmentResponseList = new ArrayList<>();
        appointmentResponseList = appointmentService.findAll();
        if (appointmentResponseList == null || appointmentResponseList.size() < 1) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(400, "Empty appointment list", null)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "All appointments", appointmentResponseList)
        );
    }

    @GetMapping("/appointment/status={statusId}")
    public ResponseEntity<ResponseObject> getAppointmentByStatusId(@PathVariable("statusId") long statusId) {

        if (statusService.findById(statusId) == null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Status is not exist !", null)
            );
        }
        List<AppointmentResponse> appointmentResponseList = new ArrayList<>();
        appointmentResponseList = appointmentService.findByStatusId(statusId);
        if (appointmentResponseList == null || appointmentResponseList.size() < 1) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Empty appointment list", appointmentResponseList)
            );
        }
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Appointment by ststusId", appointmentResponseList)
        );
    }

    @GetMapping("/appointment/appointmentId={appointmentId}")
    public ResponseEntity<ResponseObject> getAppointmentByAppointmentId(@PathVariable("appointmentId") long appointmentId) {
        AppointmentResponse appointmentResponse = appointmentService.findById(appointmentId);
        if (appointmentResponse != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Appointment by appointmentId", appointmentResponse)
            );
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Appointment is not exist !", null));
    }

    @GetMapping("/appointment/doctorId={doctorId}")
    public ResponseEntity<ResponseObject> getAppointmentByDoctorId(@PathVariable("doctorId") long doctorId) {

        List<AppointmentResponse> appointmentResponseList = new ArrayList<>();
        if (doctorService.findById(doctorId) != null) {
            appointmentResponseList = appointmentService.findByDoctorId(doctorId);
            if (appointmentResponseList == null || appointmentResponseList.size() < 1)
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "Empty appointment list", appointmentResponseList));
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Appointment by doctorId", appointmentResponseList)
            );
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Doctor is not exist", null));
    }

    @GetMapping("/appointment/phone={phone}")
    public ResponseEntity<ResponseObject> getAppointmentByDoctorId(@PathVariable("phone") String phone) {
        List<AppointmentResponse> appointmentResponseList = new ArrayList<>();
        appointmentResponseList = appointmentService.findByPhone(phone);

        if (appointmentResponseList != null || appointmentResponseList.size() > 0) {

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Appointment by phone", appointmentResponseList)
            );
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "This phone is not have appointment", null));
    }

    @GetMapping("/appointment/hospitalId={hospitalId}")
    public ResponseEntity<ResponseObject> getAppointmentByHospitalId(@PathVariable("hospitalId") long hospitalId) {

        List<AppointmentResponse> appointmentResponseList = new ArrayList<>();
        if (hospitalService.findById(hospitalId) != null) {
            appointmentResponseList = appointmentService.findByHospitalId(hospitalId);
            if (appointmentResponseList.size() < 1) {
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "Empty appointment list", appointmentResponseList)
                );
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Appointment by hospitalId", appointmentResponseList)
            );
        }

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Hospital is not exist", null));
    }


    @GetMapping("/confirm/appointmentId={appointmentId}")
    public ResponseEntity<ResponseObject> confirmAppointment(@PathVariable("appointmentId") long appointmentId) throws ParseException {

        AppointmentResponse appointmentResponse = appointmentService.findById(appointmentId);
        if (appointmentResponse == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Appointment is not exist", null));
        boolean confirm = false;
        boolean isExistTime = false;
        if (appointmentResponse.getStatusResponse().getStatusId() == 2) {
            isExistTime = appointmentService.isExistTime(appointmentId);
            if (isExistTime) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                        new ResponseObject(400, "Time is exist", null));
            }
            confirm = doctorService.changeStatusAppointment(appointmentId, 3);
        }
        if (confirm) {
            {
                AppointmentResponse appointmentResponseConfirm = appointmentService.findById(appointmentId);
                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResponseObject(200, "Confirm success", appointmentResponseConfirm)
                );
            }
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Appointment must be in state pending", null));
    }

    @GetMapping("/cancel/appointmentId={appointmentId}")
    public ResponseEntity<ResponseObject> cancelAppointment(@PathVariable("appointmentId") long appointmentId) {
        AppointmentResponse appointmentResponse = appointmentService.findById(appointmentId);
        boolean confirm = false;
        if (appointmentResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Appointment is not exist", null));
        }
        if (appointmentResponse.getStatusResponse().getStatusId() < 3) {
            confirm = doctorService.changeStatusAppointment(appointmentId, 1);
        }

        if (confirm) {
            AppointmentResponse appointmentResponseConfirm = appointmentService.findById(appointmentId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Cancel is success", appointmentResponseConfirm)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Appointment is confirmed", null));
    }

    @GetMapping("/done/appointmentId={appointmentId}")
    public ResponseEntity<ResponseObject> doneAppointment(@PathVariable("appointmentId") long appointmentId) {
        AppointmentResponse appointmentResponse = appointmentService.findById(appointmentId);
        boolean confirm = false;
        if (appointmentResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Appointment is not exist", null));
        }
        if (appointmentResponse.getStatusResponse().getStatusId() > 2) {
            confirm = doctorService.changeStatusAppointment(appointmentId, 4);
        }
        if (confirm) {
            AppointmentResponse appointmentResponseConfirm = appointmentService.findById(appointmentId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Done is success", appointmentResponseConfirm)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Appointment must be confirm", null));
    }

    @GetMapping("/re-examination/appointmentId={appointmentId}")
    public ResponseEntity<ResponseObject> reExaminationAppointment(@PathVariable("appointmentId") long appointmentId) {
        AppointmentResponse appointmentResponse = appointmentService.findById(appointmentId);
        boolean confirm = false;
        if (appointmentResponse == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Appointment is not exist", null));
        }
        if (appointmentResponse.getStatusResponse().getStatusId() > 3) {
            confirm = doctorService.changeStatusAppointment(appointmentId, 5);
        }
        if (confirm) {
            AppointmentResponse appointmentResponseConfirm = appointmentService.findById(appointmentId);
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Re-examination is success", appointmentResponseConfirm)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Appointment must be done before", null));
    }

    @PutMapping("/descriptionAppointment")
    public ResponseEntity<ResponseObject> descriptionAppointment(@RequestBody DescriptionAppointmentForm descriptionAppointmentForm) {
        AppointmentResponse appointmentResponse = appointmentService.findById(descriptionAppointmentForm.getId());
        String confirm = "";
        if (appointmentResponse == null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Appointment is not exist !", null));
        confirm = appointmentService.setDescription(descriptionAppointmentForm.getId(), descriptionAppointmentForm.getDescription());

        if (confirm.equals("Success")) {
            AppointmentResponse appointmentResponseUpdated = appointmentService.findById(appointmentResponse.getAppointmentId());
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "Set description is Success", appointmentResponseUpdated)
            );
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, confirm, null));
    }


}
