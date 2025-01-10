package africa.semicolon.koonnkt;

import africa.semicolon.koonnkt.dto.request.ReportRequest;
import africa.semicolon.koonnkt.dto.response.ReportResponse;
import africa.semicolon.koonnkt.service.ReportServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;

@SpringBootTest
public class ReportServiceImplTest {
    @Autowired
    private ReportServiceImpl reportService;
    @Test
    public void  testThatUserCanReport(){
        ReportRequest reportRequest = new ReportRequest();
        reportRequest.setReporter("Lukuman2");
        reportRequest.setReported("Azeez90");
        reportRequest.setDescription("Owe me money");
        ReportResponse response = reportService.makeReport(reportRequest);
        System.out.println(response);
        assertThat(response.getMessage()).isEqualTo("");

    }

}
