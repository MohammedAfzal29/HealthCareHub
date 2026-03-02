package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.model.Payment;

public interface PatientService {

    String recordPayment(Long appointmentId, Double amount);

    String updatePaymentStatus(Long paymentId, String status);

    Payment getPaymentById(Long paymentId);
}