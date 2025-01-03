package at.fehringer.authentication.controller.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateDiaryEntryRequest {
    private String entryTitle;
    private String entryContent;
    private LocalDate entryDate;
}
