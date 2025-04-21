package africa.semicolon.koonnkt.service;

import africa.semicolon.koonnkt.config.ConfigSecurity;
import africa.semicolon.koonnkt.data.model.PostRide;
import africa.semicolon.koonnkt.dto.request.PayStackRequest;
import africa.semicolon.koonnkt.dto.response.PayStackApiResponse;
import africa.semicolon.koonnkt.dto.response.PayStackResponse;
import africa.semicolon.koonnkt.execption.UnsuccessfulTransactionException;
import okhttp3.Response;
import okhttp3.Request;
import okhttp3.OkHttpClient;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;

import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URI;

@Service
public class PayStackServiceImpl implements PaystackService {
    @Autowired
    private PostRideServiceImpl postRideService;

    final private ConfigSecurity configSecurity;

    public PayStackServiceImpl(ConfigSecurity configSecurity) {
        this.configSecurity = configSecurity;
    }

    @Override

    public PayStackApiResponse initializePayment(String email, Long rideId) throws IOException {
        PostRide postRide = postRideService.findRideById(rideId);
        OkHttpClient client = new OkHttpClient();
        JSONObject json = new JSONObject();
        json.put("email", email);
        String value = String.valueOf(postRide.getPrice().multiply(BigDecimal.valueOf(100)));
        json.put("amount", value);
        json.put("currency", "NGN");
        json.put("callback_url", "http://localhost:8080/verify-payment/" + rideId);
        RequestBody body = RequestBody.create(json.toString(), MediaType.parse("application/json"));
        Request request = new Request.Builder()
                .url(configSecurity.getPayStackUrl())
                .post(body)
                .addHeader("Authorization", "Bearer " + configSecurity.getPayStackApi())
                .addHeader("Content-Type", "application/json")
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful())
                throw new UnsuccessfulTransactionException("Payment unsuccessful: " + response);
            String paystackResponse = response.body().string();
            return new PayStackApiResponse(true, paystackResponse);
        }
    }

    @Override
    public PayStackResponse makePayment(PayStackRequest request) throws IOException {
      request.setAmount(request.getAmount().multiply(BigDecimal.valueOf(100)));

        URI uri = URI.create(configSecurity.getPayStackUrl());
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders .setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        httpHeaders .setBearerAuth(configSecurity.getPayStackApi());

        RequestEntity<PayStackRequest> requestEntity = RequestEntity
                .post(uri)
                .headers(httpHeaders)
                .body(request);
//        HttpEntity<?> requestEntity = new org.springframework.http.HttpEntity<>(request, httpHeaders);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PayStackResponse> initResponse = restTemplate.postForEntity(uri, requestEntity, PayStackResponse.class);
        PayStackResponse payStackResponse = initResponse.getBody();
        System.out.println("Response is this --> "+ initResponse);

        if (payStackResponse == null || !payStackResponse.isStatus()){
            throw new UnsuccessfulTransactionException("PayStack transaction initialization failed!");
        }
       return payStackResponse;
    }


















    @Override
    public PayStackResponse verifyTransaction(String reference) {

//        try {
//            String url = "https://api.paystack.co/transaction/verify/" + reference;
//
//            HttpClient client = HttpClients.createDefault();
//            HttpPost request = new HttpPost(url);
//
//            request.setHeader("Authorization", "Bearer " + configSecurity.getPayStackApi());
//
//            CloseableHttpResponse response = (CloseableHttpResponse) client.execute(request);
//
//            String responseString = EntityUtils.toString(response.getEntity());
//
//            JSONObject responseObject = new JSONObject(responseString);
//            return responseObject.getJSONObject("data").getString("status").equals("success");
//        } catch (Exception e) {
//            e.printStackTrace();
//            return false;
//        }

        URI uri = URI.create(configSecurity.getStackUrl() + "/" + reference);
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
        httpHeaders.setBearerAuth(configSecurity.getPayStackApi());

        RequestEntity<Void> requestEntity = RequestEntity
                .get(uri)
                .headers(httpHeaders)
                .build();

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<PayStackResponse> response = restTemplate.exchange(uri, HttpMethod.GET, requestEntity, PayStackResponse.class);
        PayStackResponse payStackResponse = response.getBody();

        if (payStackResponse == null || !payStackResponse.isStatus()){
            throw new UnsuccessfulTransactionException("PayStack transaction verification failed!");
        }

        return payStackResponse;


    }

}
