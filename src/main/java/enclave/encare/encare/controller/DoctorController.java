package enclave.encare.encare.controller;

import enclave.encare.encare.model.Hospital;
import enclave.encare.encare.model.ResponseObject;
import enclave.encare.encare.modelResponse.CategoryResponse;
import enclave.encare.encare.modelResponse.DoctorResponse;
import enclave.encare.encare.modelResponse.HospitalResponse;
import enclave.encare.encare.service.CategoryService;
import enclave.encare.encare.service.DoctorService;
import enclave.encare.encare.service.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
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


    @GetMapping("/")
    public ResponseEntity<ResponseObject> listDoctor() {
        List<DoctorResponse> doctorResponseList = doctorService.listDoctor();
        if (doctorResponseList==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Get to fail","Cant load this page" )
            );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "List Doctor",doctorResponseList )
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
    public ResponseEntity<ResponseObject> doctorById(@PathVariable("doctorId") int doctorId){
        DoctorResponse doctorResponse  =doctorService.findById(doctorId);
        if (doctorResponse==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Doctor detail","DoctorId is not found !" )
            );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "List Category", doctorResponse)
        );
    }
    @GetMapping("/doctorName={doctorName}")
    public ResponseEntity<ResponseObject> doctorById(@PathVariable("doctorName") String doctorName){
        DoctorResponse doctorResponse  =doctorService.findByName(doctorName);
        if (doctorResponse==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "Doctor detail","DoctorId is not found !" )
            );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "List Category", doctorResponse)
        );
    }

    @GetMapping("/hospitalId={hospitalId}")
    public ResponseEntity<ResponseObject> listDoctorByHospitalId(@PathVariable("hospitalId") int hospitalId){
        HospitalResponse hospitalResponse = hospitalService.findById(hospitalId);
        if (hospitalResponse ==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "List doctor of hospital", "Hospital id invalid !")
            );
        List<DoctorResponse> doctorResponseList = doctorService.listDoctorOfHospital(hospitalId);
        if (doctorResponseList==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "List doctor of hospital", "Hospital has not doctor ")
            );
        return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "List doctor of hospital",doctorResponseList )
        );
    }
//    @GetMapping("/hospitalId={hospitalId}&page={page}")
//    public ResponseEntity<ResponseObject> listDoctorByHospitalId(@PathVariable("hospitalId") int hospitalId,@PathVariable("page") int page){
//        return ResponseEntity.status(HttpStatus.OK).body(
//                new ResponseObject(200, "List Category", doctorService.listDoctorOfHospital(hospitalId,page))
//        );
//    }
    @GetMapping("/categoryId={categoryId}")
    public ResponseEntity<ResponseObject> listDoctorByCategoryId(@PathVariable("categoryId") int categoryId){
        CategoryResponse categoryResponse = categoryService.findById(categoryId);
        String description  = "List doctor of category";
        if(categoryResponse==null)
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, description, "Category invalid")
            );
        List<DoctorResponse> doctorResponseList = doctorService.listDoctorOfCategory(categoryId);
        if (doctorResponseList==null || doctorResponseList.size()<1)
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
    @PostMapping("/confirm/appointmentId={appointmentId}")
    public ResponseEntity<ResponseObject> confirmAppointment(@PathVariable("appointmentId") long  appointmentId){
        boolean confirm = doctorService.changeStatusAppointment(appointmentId,2);
        if (confirm)
            return ResponseEntity.status(HttpStatus.OK).body(
                new ResponseObject(200, "Confirm appointment", "confirm")
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Confirm cancel", "Failed to confirm"));
    }
    @PostMapping("/cancel/appointmentId={appointmentId}")
    public ResponseEntity<ResponseObject> cancelAppointment(@PathVariable("appointmentId") long  appointmentId){
        boolean confirm = doctorService.changeStatusAppointment(appointmentId,4);
        if (confirm)
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResponseObject(200, "cancel appointment", "confirm")
            );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                new ResponseObject(400, "Confirm cancel", "Failed to confirm"));
    }




}
