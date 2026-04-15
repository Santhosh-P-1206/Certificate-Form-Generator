package com.example.certificate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/certificate")
@CrossOrigin(origins = "http://localhost:3000")
public class CertificateController {

    @Autowired
    private CertificateService service;

    @GetMapping("/test")
    public String test() {
        return "API Working Successfully";
    }

    @PostMapping("/generate")
    public ResponseEntity<byte[]> generateCertificate(@RequestBody Certificate cert) {

        Certificate saved = service.saveCertificate(cert);

        byte[] pdf = service.generatePdf(saved);

        return ResponseEntity.ok()
                .contentType(org.springframework.http.MediaType.APPLICATION_PDF)
                .header(org.springframework.http.HttpHeaders.CONTENT_DISPOSITION, "inline; filename=certificate.pdf")
                .body(pdf);
    }
}