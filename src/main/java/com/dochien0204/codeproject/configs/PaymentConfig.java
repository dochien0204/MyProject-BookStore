package com.dochien0204.codeproject.configs;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;

public class PaymentConfig {
    public static final String vnp_Version = "2.1.0";
    public static final String vnp_Command = "pay";
    public static final String vnp_TmnCode = "X0AJ3GGE";
    public static final String vnp_CurrCode = "VND";
    public static final String vnp_IpAddr = "0:0:0:0:0:0:0:1";
    public static final String vnp_Locale = "vn";
    public static final String vnp_OrderType = "150000";
    public static final String vnp_ReturnUrl = "https://sandbox.vnpayment.vn/tryitnow/Home/VnPayIPN";
    public static final String vnp_HashSecret = "DYCOLKHEQTSDTWFPERNVRVCEGBWXXYRD";
    public static final String vnp_PayUrl = " https://sandbox.vnpayment.vn/paymentv2/vpcpay.html";

    public static String hmacSHA512(final String key, final String data) {
        try {

            if (key == null || data == null) {
                throw new NullPointerException();
            }
            final Mac hmac512 = Mac.getInstance("HmacSHA512");
            byte[] hmacKeyBytes = key.getBytes();
            final SecretKeySpec secretKey = new SecretKeySpec(hmacKeyBytes, "HmacSHA512");
            hmac512.init(secretKey);
            byte[] dataBytes = data.getBytes(StandardCharsets.UTF_8);
            byte[] result = hmac512.doFinal(dataBytes);
            StringBuilder sb = new StringBuilder(2 * result.length);
            for (byte b : result) {
                sb.append(String.format("%02x", b & 0xff));
            }
            return sb.toString();

        } catch (Exception ex) {
            return "";
        }
    }
}
