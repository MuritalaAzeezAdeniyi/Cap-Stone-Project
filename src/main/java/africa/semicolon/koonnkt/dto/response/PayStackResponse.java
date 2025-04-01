package africa.semicolon.koonnkt.dto.response;

public class PayStackResponse {
private boolean status;
private String message;
private String reference;
private String authorizationUrl;
private PayStackDataResponse data;

    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public PayStackDataResponse getData() {
        return data;
    }

    public void setData(PayStackDataResponse data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "PayStackResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
