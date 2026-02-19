package nimblix.in.HealthCareHub.serviceimpl;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.model.Bill;
import nimblix.in.HealthCareHub.model.Payment;
import nimblix.in.HealthCareHub.repository.BillRepository;
import nimblix.in.HealthCareHub.repository.PaymentRepository;
import nimblix.in.HealthCareHub.service.PaymentService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BillRepository billRepository;

    @Override
    public Payment payBill(Long billId, Double amount) {

        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new RuntimeException("Bill not found"));

        Payment payment = new Payment();
        payment.setAmount(amount);
        payment.setPaymentStatus("SUCCESS");
        payment.setPaymentDate(LocalDateTime.now());
        payment.setBill(bill);

        bill.setStatus("PAID");

        billRepository.save(bill);

        return paymentRepository.save(payment);
    }
}
