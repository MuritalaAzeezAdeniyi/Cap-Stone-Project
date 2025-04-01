package africa.semicolon.koonnkt.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PayStackDataResponse {

    @JsonProperty("authorization_url")
    private String authorizationUrl;
    @JsonProperty("access_code")
    private String accessCode;
    private String reference;


    public String getAuthorizationUrl() {
        return authorizationUrl;
    }

    public void setAuthorizationUrl(String authorizationUrl) {
        this.authorizationUrl = authorizationUrl;
    }

    public String getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(String accessCode) {
        this.accessCode = accessCode;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    @Override
    public String toString() {
        return "PayStackDataResponse{" +
                "authorizationUrl='" + authorizationUrl + '\'' +
                ", accessCode='" + accessCode + '\'' +
                ", reference='" + reference + '\'' +
                '}';
    }
}