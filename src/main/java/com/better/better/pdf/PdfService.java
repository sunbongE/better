package com.better.better.pdf;

import com.itextpdf.io.font.FontProgram;
import com.itextpdf.io.font.FontProgramFactory;
import com.itextpdf.io.font.PdfEncodings;
 import com.itextpdf.kernel.colors.DeviceCmyk;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.colorspace.PdfColorSpace;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.awt.*;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
public class PdfService {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy년 MM월 dd일");
    private static final String FONT_PATH = "fonts/NotoSansKR-VariableFont_wght.ttf";

    public byte[] generatePdf(PdfRequestDto request) {
        PageSize pageSize = PageSize.A4.rotate(); // 가로 A4

        try (ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
            PdfWriter writer = new PdfWriter(baos);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf, pageSize);

            // 너비/높이 가져오기
            float pageWidth = pageSize.getWidth();
            float pageHeight = pageSize.getHeight();

            // 폰트 지정
            // resources/fonts/NanumGothic.ttf 경로의 폰트 사용

            // 📂 폰트 리소스 로드
            ClassPathResource fontResource = new ClassPathResource(FONT_PATH);

            // ✅ FontProgram 객체로 변환
            FontProgram fontProgram = FontProgramFactory.createFont(
                    fontResource.getPath() // getPath는 파일 경로 문자열
            );

            // ✅ PdfFont 생성 (한글 인코딩, 폰트 내장)
            PdfFont koreanFont = PdfFontFactory.createFont(
                    fontProgram,
                    PdfEncodings.IDENTITY_H,
                    PdfFontFactory.EmbeddingStrategy.PREFER_EMBEDDED
            );


            // 제목 : 휴일 이유
            Paragraph title = new Paragraph(request.getHolidayReason() + " 휴무 안내")
                    .setFont(koreanFont)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(24)
                    .setBold();
//            document.add(title);

            // 상호명
            String storeName =request.getStoreName();

            Paragraph storeNameParagraph = new Paragraph(storeName)
                    .setFont(koreanFont)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(21);

            // 휴무일
            String holydayDate = String.format("%s ~ %s 휴무입니다.",
                    request.getStartDate().format(DATE_FORMATTER),
                    request.getEndDate().format(DATE_FORMATTER));

            Paragraph holydayDateParagraph = new Paragraph(holydayDate)
                    .setFont(koreanFont)
                    .setFontColor(ColorConstants.RED)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFontSize(21);

            // 텍스트 묶음 → 한 번에 가운데 위치
            Div container = new Div()
                    .add(title)
                    .add(storeNameParagraph)
                    .add(holydayDateParagraph)
                    .setTextAlignment(TextAlignment.CENTER)
                    .setFixedPosition(
                            0,
                            pageHeight / 2 - 50, // 중간보다 약간 위로 (폰트 크기 고려)
                            pageWidth
                    );
            document.add(container);
            document.close();
            return baos.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException("PDF 생성 중 오류가 발생했습니다.", e);
        }
    }
} 