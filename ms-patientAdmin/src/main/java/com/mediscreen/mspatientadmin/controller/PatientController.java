package com.mediscreen.mspatientadmin.controller;

import com.mediscreen.mspatientadmin.exception.NotFoundException;
import com.mediscreen.mspatientadmin.interfaces.PatientServiceInterface;
import com.mediscreen.mspatientadmin.interfaces.SecurityServiceInterface;
import com.mediscreen.mspatientadmin.model.Patient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class PatientController {

    @Autowired
    private PatientServiceInterface patientService;

    @Autowired
    private SecurityServiceInterface securityService;

    @GetMapping("/patient/getAll")
    public List<Patient> getAllPatients(@RequestHeader("token") String token) {
        securityService.authenticationCheck(token);
        List<Patient> patientList = patientService.getAllPatient();
        if (patientList == null) throw new NotFoundException("No data found");
        return patientList;
    }

    @GetMapping("/patient/search")
    public List<Patient> searchPatients(@RequestHeader("token") String token, @RequestParam(required = true) String search) {
        securityService.authenticationCheck(token);
        List<Patient> patientList = patientService.searchPatient(search);
        if (patientList == null) throw new NotFoundException("No data found");
        return patientList;
    }

    @GetMapping("/patient/get/{id}")
    public Patient getPatient(@RequestHeader("token") String token, @PathVariable int id) {
        securityService.authenticationCheck(token);
        Patient patient = patientService.getPatientById(id);
        if (patient == null) throw new NotFoundException("Unknown patient with id : " + id);
        return patient;
    }

    @PostMapping("/patient/create")
    public ResponseEntity<Patient> createPatient(@RequestHeader("token") String token, @Valid @RequestBody Patient patient){
        securityService.authenticationCheck(token);
        Patient newPatient = patientService.createPatient(patient);
        if (newPatient == null) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(newPatient, HttpStatus.CREATED);
    }

    @PutMapping("/patient/update")
    public ResponseEntity<Patient> updatePatient(@RequestHeader("token") String token, @Valid @RequestBody Patient patient){
        securityService.authenticationCheck(token);
        Patient updatedPatient = patientService.updatePatient(patient);
        if (updatedPatient == null) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(updatedPatient, HttpStatus.OK);
    }

    @DeleteMapping("/patient/delete/{id}")
    public ResponseEntity<Void> deletePatient(@RequestHeader("token") String token, @PathVariable int id){
        securityService.authenticationCheck(token);
        if (!patientService.deletePatientById(id)) return ResponseEntity.noContent().build();
        return ResponseEntity.ok().build();
    }
}
