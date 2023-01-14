package com.dochien0204.codeproject.services;

import com.dochien0204.codeproject.dtos.payment.PaymentDTO;
import com.dochien0204.codeproject.dtos.payment.PaymentResponse;

public interface PaymentService {
    PaymentResponse paymentOrder(PaymentDTO paymentDTO, String orderId);

}
