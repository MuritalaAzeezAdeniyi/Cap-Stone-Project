package africa.semicolon.koonnkt.service;

import africa.semicolon.koonnkt.dto.request.ReportRequest;
import africa.semicolon.koonnkt.dto.response.ReportResponse;

public interface ReportService {
    ReportResponse makeReport(ReportRequest reportRequest);

}
