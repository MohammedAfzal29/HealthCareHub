package nimblix.in.HealthCareHub.service;

import nimblix.in.HealthCareHub.model.Payment;

public interface PaymentService {

    Payment payBill(Long billId, Double amount);

}
