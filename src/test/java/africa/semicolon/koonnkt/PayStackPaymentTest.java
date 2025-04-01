package africa.semicolon.koonnkt;

import africa.semicolon.koonnkt.dto.request.PayStackRequest;
import africa.semicolon.koonnkt.dto.response.PayStackResponse;
import africa.semicolon.koonnkt.service.PayStackServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class PayStackPaymentTest {
    @Autowired
    private PayStackServiceImpl payStackService;
 @Test
    public void testThaCanTransferToPayStackService() throws IOException {
        PayStackRequest request = new PayStackRequest();
        request.setEmail("azeezmuritalaadeniyi@gmail.com");
        request.setAmount(BigDecimal.valueOf(8000));

        PayStackResponse response = payStackService.makePayment(request);
        assertThat(response).isNotNull();
        assertThat(response.getMessage()).isNotNull();
        assertThat(response.getData().getAuthorizationUrl()).isNotNull();
        assertThat(response.getData().getReference()).isNotNull();
        System.out.println(response.getAuthorizationUrl() + response.getReference() + response.getMessage());
    }
}
