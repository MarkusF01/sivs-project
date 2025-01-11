package at.fehringer.authentication.controller.dto;

import lombok.Data;

@Data
public class DiaryEntryResponseData {
    private Integer id;
    private String entryTitle;
    private String entryContent;
    private String entryDate;
}
