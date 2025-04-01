package africa.semicolon.koonnkt.dto.response;

import java.math.BigDecimal;

public class PayStackApiResponse {

        private boolean success;
        private String paystackResponse;

        // Constructor
        public PayStackApiResponse(boolean success, String paystackResponse) {
            this.success = success;
            this.paystackResponse = paystackResponse;
        }

        // Getters and Setters
        public boolean isSuccess() {
            return success;
        }

        public void setSuccess(boolean success) {
            this.success = success;
        }

        public String getPaystackResponse() {
            return paystackResponse;
        }

        public void setPaystackResponse(String paystackResponse) {
            this.paystackResponse = paystackResponse;
        }



}
