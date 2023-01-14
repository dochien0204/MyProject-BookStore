package com.dochien0204.codeproject.controllers;

import com.dochien0204.codeproject.base.RestApiV1;
import com.dochien0204.codeproject.base.VsResponseUtil;
import com.dochien0204.codeproject.dtos.payment.PaymentDTO;
import com.dochien0204.codeproject.dtos.payment.PaymentResponse;
import com.dochien0204.codeproject.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestApiV1
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment/{orderId}/payment-order")
    public ResponseEntity<?> paymentOrder(@RequestBody PaymentDTO paymentDTO,
                                          @PathVariable(name = "orderId") String orderId) {
        PaymentResponse paymentResponse = paymentService.paymentOrder(paymentDTO, orderId);
        return VsResponseUtil.ok(paymentResponse);
    }
}
