package com.better.better.pdf;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PdfRequestDto {
    private LocalDate startDate;
    private LocalDate endDate;
    private String storeName;
    private String holidayReason;
} 