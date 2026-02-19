package nimblix.in.HealthCareHub.controller;

import lombok.RequiredArgsConstructor;
import nimblix.in.HealthCareHub.model.Payment;
import nimblix.in.HealthCareHub.service.PaymentService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/pay")
    public Payment payBill(@RequestParam Long billId,
                           @RequestParam Double amount) {
        return paymentService.payBill(billId, amount);
    }
}
