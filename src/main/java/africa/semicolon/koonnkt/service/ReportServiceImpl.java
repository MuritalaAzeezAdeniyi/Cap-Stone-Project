package africa.semicolon.koonnkt.service;

import africa.semicolon.koonnkt.data.model.Report;
import africa.semicolon.koonnkt.data.model.Users;
import africa.semicolon.koonnkt.data.repository.ReportRepo;
import africa.semicolon.koonnkt.data.repository.UsersRepo;
import africa.semicolon.koonnkt.dto.request.ReportRequest;
import africa.semicolon.koonnkt.dto.response.ReportResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReportServiceImpl implements ReportService {
    @Autowired
    private UsersRepo usersRepository;
    @Autowired
    private ReportRepo reportRepository;
    @Autowired
    private NotificationService notificationService;

    private static final String  ADMIN_EMAIL= "azeezmuritalaadeniyi@gmail.com";
    @Override
    public ReportResponse makeReport(ReportRequest reportRequest) {
        Users reporter = usersRepository.findByUsername(reportRequest.getReporter());
        Users reported = usersRepository.findByUsername(reportRequest.getReported());
        if (reporter != null && reported != null) {
            Report report = new Report();
            report.setReporter(reporter);
            report.setReported(reported);
            report.setDescription(reportRequest.getDescription());
            report.setReportDate(LocalDateTime.now());
            reportRepository.save(report);

            String subject = "New report Submitted";
            String text = String.format("A new report has been submitted by %s against %s. Description: %s",
                    reporter.getUsername(), reported.getUsername(), reportRequest.getDescription());

            notificationService.sendEmail(ADMIN_EMAIL, subject, text);
            ReportResponse response = new ReportResponse();
            response.setDescription(reportRequest.getDescription());
            response.setReported(String.valueOf(reported));
            response.setReporter(String.valueOf(reporter));
            return response;

        }else {
            throw new RuntimeException("Username not found");
        }

    }
}
