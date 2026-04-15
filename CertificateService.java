package com.example.certificate;

import java.util.List;
import com.itextpdf.kernel.geom.PageSize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.layout.*;
import com.itextpdf.layout.element.*;
import java.io.ByteArrayOutputStream;

import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.layout.element.Image;
import java.io.InputStream;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.io.font.constants.StandardFonts;


@Service
public class CertificateService {

    @Autowired
    private CertificateRepository repo;
    public Certificate saveCertificate(Certificate cert) {
        return repo.save(cert);
    }

    public List<Certificate> getAllCertificates() {
        return repo.findAll();
    }
    public Certificate getById(long id) {
        return repo.findById(id).orElseThrow();
    }

    public byte[] generatePdf(Certificate cert) {

        ByteArrayOutputStream out = new ByteArrayOutputStream();

        try {
            PdfWriter writer = new PdfWriter(out);
            PdfDocument pdf = new PdfDocument(writer);
            Document doc = new Document(pdf, PageSize.A4);

            pdf.addNewPage();

            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont normalFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);

            InputStream is = getClass().getClassLoader().getResourceAsStream("c1.jpg.jpeg");

            if (is != null) {
                byte[] bytes = is.readAllBytes();

                Image bg = new Image(ImageDataFactory.create(bytes));bg.scaleAbsolute(PageSize.A4.getWidth(), PageSize.A4.getHeight() / 2);
                bg.setFixedPosition(0, PageSize.A4.getHeight() / 2);
                doc.add(bg);
            }

            Canvas canvas = new Canvas(pdf.getFirstPage(), pdf.getDefaultPageSize());

            canvas.setFont(boldFont);
            canvas.setFontSize(26);
            canvas.showTextAligned("CERTIFICATE", 297, 900, TextAlignment.CENTER);

            canvas.setFont(normalFont);
            canvas.setFontSize(14);
            canvas.showTextAligned("This is to certify that", 297, 650, TextAlignment.CENTER);


            canvas.setFont(boldFont);
            canvas.setFontSize(22);
            canvas.showTextAligned(cert.getName().toUpperCase(), 297, 600, TextAlignment.CENTER);

            canvas.setFont(normalFont);
            canvas.setFontSize(14);
            canvas.showTextAligned("has successfully completed", 297, 560, TextAlignment.CENTER);

            canvas.setFont(boldFont);
            canvas.setFontSize(18);
            canvas.showTextAligned(cert.getCourse().toUpperCase(), 297, 520, TextAlignment.CENTER);

            canvas.setFont(normalFont);
            canvas.setFontSize(12);
            canvas.showTextAligned("Date: " + cert.getDate(), 297, 480, TextAlignment.CENTER);

            canvas.close();
            doc.close();
            pdf.close();

        } catch (Exception e) {
            throw new RuntimeException("PDF generation failed", e);
        }

        return out.toByteArray();
    } }