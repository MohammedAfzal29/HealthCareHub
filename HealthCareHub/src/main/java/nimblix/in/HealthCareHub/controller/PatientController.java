package nimblix.in.HealthCareHub.controller;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.model.Payment;
import nimblix.in.HealthCareHub.service.PatientService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/patient")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;

    @PostMapping("/payment/{appointmentId}")
    public ResponseEntity<String> recordPayment(
            @PathVariable Long appointmentId,
            @RequestParam Double amount) {

        return ResponseEntity.ok(
                patientService.recordPayment(appointmentId, amount));
    }

    @PutMapping("/payment/{paymentId}")
    public ResponseEntity<String> updatePaymentStatus(
            @PathVariable Long paymentId,
            @RequestParam String status) {

        return ResponseEntity.ok(
                patientService.updatePaymentStatus(paymentId, status));
    }

    @GetMapping("/payment/{paymentId}")
    public ResponseEntity<Payment> getPayment(
            @PathVariable Long paymentId) {

        return ResponseEntity.ok(
                patientService.getPaymentById(paymentId));
    }
}