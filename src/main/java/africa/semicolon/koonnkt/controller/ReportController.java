package africa.semicolon.koonnkt.controller;

import africa.semicolon.koonnkt.dto.request.ReportRequest;
import africa.semicolon.koonnkt.dto.response.ApiResponse;
import africa.semicolon.koonnkt.dto.response.ReportResponse;
import africa.semicolon.koonnkt.service.ReportService;
import africa.semicolon.koonnkt.service.ReportServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/report")
public class ReportController {
    @Autowired
    private ReportServiceImpl reportService;

    @PostMapping("/makeReport")
    @PreAuthorize("hasAnyRole('PASSENGER','DRIVER')")
    public ResponseEntity<?>  makeReport(@RequestBody ReportRequest reportRequest) {
        try{
            ReportResponse reportResponse = reportService.makeReport(reportRequest);
            return new ResponseEntity<>(new ApiResponse(true, reportResponse), HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<> (e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

}
