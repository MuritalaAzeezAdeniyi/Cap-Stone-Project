package africa.semicolon.koonnkt.dto.request;

public class PayStackInitializeRequest {
    private PayStackRequest data;

    @Override
    public String toString() {
        return "PayStackInitializeRequest{" +
                "data=" + data +
                '}';
    }

    public PayStackRequest getData() {
        return data;
    }

    public void setData(PayStackRequest data) {
        this.data = data;
    }
}
