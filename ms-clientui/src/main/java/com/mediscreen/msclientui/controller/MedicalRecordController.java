package com.mediscreen.msclientui.controller;

import com.mediscreen.msclientui.exception.NotFoundException;
import com.mediscreen.msclientui.interfaces.MedicalRecordServiceInterface;
import com.mediscreen.msclientui.model.MedicalRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RestController
public class MedicalRecordController {
    @Autowired
    private MedicalRecordServiceInterface medicalRecordService;

    @PostMapping("/medical-record/create/{id}")
    public ResponseEntity<MedicalRecord> createMedicalRecord(HttpSession session, @PathVariable(value="id") Integer id, @ModelAttribute MedicalRecord medicalRecord){
        try {
            medicalRecord.setPatientId(id);
            MedicalRecord newMedicalRecord = medicalRecordService.createMedicalRecord(session, medicalRecord);
            return new ResponseEntity<>(newMedicalRecord, HttpStatus.CREATED);
        } catch (RuntimeException e){
            throw new NotFoundException(e.getMessage());
        }
    }

    @GetMapping("/medical-record/update")
    public ResponseEntity<Void> updateMedicalRecord(HttpSession session, @RequestParam(value = "id") String id, @RequestParam(value = "patientId") Integer patientId, @RequestParam(value = "active") Boolean active) {
        try {
            medicalRecordService.updateMedicalRecord(session, id, patientId, active);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (RuntimeException e){
            throw new NotFoundException(e.getMessage());
        }
    }
}
