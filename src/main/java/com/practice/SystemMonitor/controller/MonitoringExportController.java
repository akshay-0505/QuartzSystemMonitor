package com.practice.SystemMonitor.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.io.File;

@RestController
@RequestMapping("/api/monitor")
public class MonitoringExportController {

    @Value("${monitoring.csv.path:logs/monitoring.csv}")
    private String csvFilePath;

    @GetMapping("/download")
    public ResponseEntity<Resource> downloadMonitoringData() {
        File csvFile = new File(csvFilePath);
        if (!csvFile.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        FileSystemResource resource = new FileSystemResource(csvFile);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType("text/csv"));
        headers.setContentDisposition(ContentDisposition.attachment()
                .filename(csvFile.getName())
                .build());

        return new ResponseEntity<>(resource, headers, HttpStatus.OK);
    }
}
