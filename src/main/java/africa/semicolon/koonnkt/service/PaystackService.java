package africa.semicolon.koonnkt.service;

import africa.semicolon.koonnkt.dto.request.PayStackRequest;
import africa.semicolon.koonnkt.dto.response.PayStackApiResponse;
import africa.semicolon.koonnkt.dto.response.PayStackResponse;

import java.io.IOException;

public interface PaystackService {
 PayStackApiResponse  initializePayment(String email, Long rideId) throws IOException;
 PayStackResponse makePayment(PayStackRequest request) throws IOException;
 PayStackResponse verifyTransaction(String reference);
}
