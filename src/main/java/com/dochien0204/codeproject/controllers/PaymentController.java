package com.dochien0204.codeproject.controllers;

import com.dochien0204.codeproject.base.RestApiV1;
import com.dochien0204.codeproject.base.VsResponseUtil;
import com.dochien0204.codeproject.dtos.payment.PaymentDTO;
import com.dochien0204.codeproject.dtos.payment.PaymentResponse;
import com.dochien0204.codeproject.services.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/payment/info-details")
    public ResponseEntity<?> resultPaymentOrder(@RequestParam(name = "vnp_Amount") Integer vnp_Amount,
                                                @RequestParam(name = "vnp_BankCode") String vnp_BankCode,
                                                @RequestParam(name = "vnp_BankTranNo") String vnp_BankTranNo,
                                                @RequestParam(name = "vnp_CardType") String vnp_CardType,
                                                @RequestParam(name = "vnp_OrderInfo") String vnp_OrderInfo,
                                                @RequestParam(name = "vnp_PayDate") Long vnp_PayDate,
                                                @RequestParam(name = "vnp_ResponseCode") Integer vnp_ResponseCode,
                                                @RequestParam(name = "vnp_TmnCode") String vnp_TmnCode,
                                                @RequestParam(name = "vnp_TransactionNo") Integer vnp_TransactionNo,
                                                @RequestParam(name = "vnp_TransactionStatus") Integer vnp_TransactionStatus,
                                                @RequestParam(name = "vnp_TxnRef") String vpn_TxnRef,
                                                @RequestParam(name = "vnp_SecureHash") String vnp_SecureHash){
        return VsResponseUtil.ok("GD thanh cong");
    }
}
