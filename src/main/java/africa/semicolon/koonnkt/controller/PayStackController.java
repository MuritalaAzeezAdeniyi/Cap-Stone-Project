package africa.semicolon.koonnkt.controller;

import africa.semicolon.koonnkt.dto.request.PayStackRequest;
import africa.semicolon.koonnkt.dto.response.PayStackResponse;
import africa.semicolon.koonnkt.execption.UnsuccessfulTransactionException;
import africa.semicolon.koonnkt.service.PayStackServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@CrossOrigin( origins = "*")
public class PayStackController {

    @Autowired
    private PayStackServiceImpl payStackService;

    @PostMapping("/initialize")
    public ResponseEntity<?> makePayment(@RequestBody PayStackRequest payStackRequest) {
        try {
            PayStackResponse payStackResponse = payStackService.makePayment(payStackRequest);
//            payStackResponse.setAuthorizationUrl(payStackResponse?null: "");
            return new ResponseEntity<>(payStackResponse,HttpStatus.CREATED);
        } catch (Exception e) {
           return new ResponseEntity<>(e.getMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/verify/{reference}")
    public ResponseEntity<PayStackResponse> verifyPayment(@PathVariable String reference) {
        try {
            PayStackResponse response = payStackService.verifyTransaction(reference);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (UnsuccessfulTransactionException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

}
