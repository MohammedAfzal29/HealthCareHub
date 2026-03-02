package nimblix.in.HealthCareHub.serviceImpl;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import nimblix.in.HealthCareHub.constants.HealthCareConstants;
import nimblix.in.HealthCareHub.exception.PaymentException;
import nimblix.in.HealthCareHub.model.Appointment;
import nimblix.in.HealthCareHub.model.Payment;
import nimblix.in.HealthCareHub.service.PatientService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PatientServiceImpl implements PatientService {

    private static final Logger logger =
            LoggerFactory.getLogger(PatientServiceImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public String recordPayment(Long appointmentId, Double amount) {

        logger.info("Recording payment for appointment ID: {}", appointmentId);

        if (appointmentId == null) {
            throw new PaymentException(HealthCareConstants.APPOINTMENT_ID_NULL);
        }

        if (amount == null || amount <= 0) {
            throw new PaymentException(HealthCareConstants.INVALID_AMOUNT);
        }

        Appointment appointment =
                entityManager.find(Appointment.class, appointmentId);

        if (appointment == null) {
            throw new PaymentException(HealthCareConstants.APPOINTMENT_NOT_FOUND);
        }

        if ("COMPLETED".equalsIgnoreCase(appointment.getStatus())) {
            throw new PaymentException(HealthCareConstants.PAYMENT_NOT_ALLOWED);
        }

        Payment payment = new Payment();
        payment.setAppointment(appointment);
        payment.setAmount(amount);
        payment.setPaymentStatus("PAID"); // Correct field
        payment.setPaymentDate(LocalDateTime.now());

        entityManager.persist(payment);

        appointment.setStatus("COMPLETED");
        entityManager.merge(appointment);

        return HealthCareConstants.PAYMENT_SUCCESS;
    }

    @Override
    @Transactional
    public String updatePaymentStatus(Long paymentId, String status) {

        if (paymentId == null) {
            throw new PaymentException(HealthCareConstants.PAYMENT_ID_NULL);
        }

        if (status == null || status.isBlank()) {
            throw new PaymentException(HealthCareConstants.STATUS_EMPTY);
        }

        Payment payment =
                entityManager.find(Payment.class, paymentId);

        if (payment == null) {
            throw new PaymentException(HealthCareConstants.PAYMENT_NOT_FOUND);
        }

        payment.setPaymentStatus(status.toUpperCase());
        entityManager.merge(payment);

        return HealthCareConstants.PAYMENT_UPDATED;
    }

    @Override
    public Payment getPaymentById(Long paymentId) {

        if (paymentId == null) {
            throw new PaymentException(HealthCareConstants.PAYMENT_ID_NULL);
        }

        Payment payment =
                entityManager.find(Payment.class, paymentId);

        if (payment == null) {
            throw new PaymentException(HealthCareConstants.PAYMENT_NOT_FOUND);
        }

        return payment;
    }
}