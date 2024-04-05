package com.kelompokdua.booking.service.Impl;

import com.kelompokdua.booking.entity.Payment;
import com.kelompokdua.booking.entity.RoomBooking;
import com.kelompokdua.booking.model.request.PaymentDetailRequest;
import com.kelompokdua.booking.model.request.PaymentRequest;
import com.kelompokdua.booking.repository.PaymentRepository;
import com.kelompokdua.booking.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.List;
import java.util.Map;

@Service
public class PaymentServieImpl implements PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private RestClient restClient;

    @Value("${midtrans.api.key}")
    private String SECRET_KEY;
    @Value("${midtrans.api.snap-url}")
    private String BASE_SNAP_URL;


    @Override
    public Payment create(RoomBooking roomBooking) {
        PaymentDetailRequest paymentDetailRequest = PaymentDetailRequest.builder()
                .orderId(roomBooking.getId())
                .amount(roomBooking.getTotalPrice())
                .build();
        PaymentRequest paymentRequest = PaymentRequest.builder()
                .paymentDetailRequest(paymentDetailRequest)
                .paymentMethod(
                        List.of(
                                "shopeepay",
                                "gopay",
                                "indomaret"
                        )
                )
                .build();
        ResponseEntity<Map<String, String>> response = restClient.post()
                .uri(BASE_SNAP_URL)
                .body(paymentRequest)
                .header(HttpHeaders.AUTHORIZATION,  "Basic " + SECRET_KEY)
                .retrieve().toEntity(new ParameterizedTypeReference<Map<String, String>>() {
                });
        Map<String, String> body = response.getBody();
        assert body != null;
        Payment payment = Payment.builder()
                .token(body.get("token"))
                .redirectUrl(body.get("redirect_url"))
                .transactionStatus("ordered")
                .build();
        return paymentRepository.saveAndFlush(payment);

    }
}

