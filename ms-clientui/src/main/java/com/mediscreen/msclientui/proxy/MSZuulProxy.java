package com.mediscreen.msclientui.proxy;

import com.mediscreen.msclientui.model.Jwt;
import com.mediscreen.msclientui.model.Login;
import com.mediscreen.msclientui.model.MedicalRecord;
import com.mediscreen.msclientui.model.Patient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@FeignClient(name = "ems-zuul")
public interface MSZuulProxy {
    // ms-authentication
    @PostMapping("/ms-authentication/generate-token")
    ResponseEntity<Jwt> msAuthentication_generateToken(@Valid @RequestBody Login login);

    @GetMapping("/ms-authentication/validate-token")
    ResponseEntity<Void> msAuthentication_validateToken(@RequestParam("token") String token);

    // ms-patientAdmin
    @GetMapping("/ms-patientadmin/patient/getAll")
    List<Patient> msPatientAdmin_getAllPatients(@RequestHeader("token") String token);

    @GetMapping("/ms-patientadmin/patient/search")
    List<Patient> searchPatients(@RequestHeader("token") String token, @RequestParam(required = true) String search);

    @GetMapping("/ms-patientadmin/patient/get/{id}")
    Patient msPatientAdmin_getPatient(@RequestHeader("token") String token, @PathVariable int id);

    @PostMapping("/ms-patientadmin/patient/create")
    ResponseEntity<Patient> msPatientAdmin_createPatient(@RequestHeader("token") String token, @Valid @RequestBody Patient patient);

    @PutMapping("/ms-patientadmin/patient/update")
    ResponseEntity<Patient> msPatientAdmin_updatePatient(@RequestHeader("token") String token, @Valid @RequestBody Patient patient);

    @DeleteMapping("/ms-patientadmin/patient/delete/{id}")
    ResponseEntity<Void> msPatientAdmin_deletePatient(@RequestHeader("token") String token, @PathVariable int id);

    @GetMapping("/ms-medicalrecord/medical-record/getAll/{id}")
    List<MedicalRecord> msMedicalRecord_getAllPatientMedicalRecords(@RequestHeader("token") String token, @PathVariable int id);

    @PostMapping("/ms-medicalrecord//medical-record/create")
    ResponseEntity<MedicalRecord> msMedicalRecords_createMedicalRecord(@RequestHeader("token") String token, @Valid @RequestBody MedicalRecord medicalRecord);

    @PutMapping("/ms-medicalrecord//medical-record/update")
    ResponseEntity<MedicalRecord> msMedicalRecords_updateMedicalRecord(@RequestHeader("token") String token, @Valid @RequestBody MedicalRecord medicalRecord);
}
