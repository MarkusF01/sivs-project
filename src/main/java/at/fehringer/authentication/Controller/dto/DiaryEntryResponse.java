package at.fehringer.authentication.Controller.dto;

import lombok.Data;

@Data
public class DiaryEntryResponse {
    private Integer id;
    private String entryTitle;
    private String entryContent;
    private String entryDate;
}
