package com.mediscreen.msmedicalrecord.controller;

import com.mediscreen.msmedicalrecord.exception.NoContent;
import com.mediscreen.msmedicalrecord.interfaces.MedicalRecordServiceInterface;
import com.mediscreen.msmedicalrecord.interfaces.SecurityServiceInterface;
import com.mediscreen.msmedicalrecord.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
public class MedicalRecordController {
    @Autowired
    private MedicalRecordServiceInterface medicalRecordService;

    @Autowired
    private SecurityServiceInterface securityService;

    @GetMapping("/medical-record/getAll/{id}")
    public List<MedicalRecord> getAllPatientMedicalRecords(@RequestHeader("token") String token, @PathVariable int id) {
        securityService.authenticationCheck(token);
        List<MedicalRecord> medicalRecordList = medicalRecordService.getPatientMedicalRecords(token, id);
        if(medicalRecordList == null || medicalRecordList.size() == 0) throw new NoContent("No data found");
        return medicalRecordList;
    }

    @PostMapping("/medical-record/create")
    public ResponseEntity<MedicalRecord> createMedicalRecord(@RequestHeader("token") String token, @Valid @RequestBody MedicalRecord medicalRecord){
        securityService.authenticationCheck(token);
        MedicalRecord newMedicalRecord = medicalRecordService.createMedicalRecord(token, medicalRecord);
        if (newMedicalRecord == null) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(newMedicalRecord, HttpStatus.CREATED);
    }

    @PutMapping("/medical-record/update")
    public ResponseEntity<MedicalRecord> updateMedicalRecord(@RequestHeader("token") String token, @Valid @RequestBody MedicalRecord medicalRecord){
        securityService.authenticationCheck(token);
        MedicalRecord updateMedicalRecord = medicalRecordService.updateMedicalRecord(token, medicalRecord);
        if (updateMedicalRecord == null) return ResponseEntity.noContent().build();
        return new ResponseEntity<>(updateMedicalRecord, HttpStatus.OK);
    }
}
