package at.fehringer.authentication.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data @AllArgsConstructor
public class DiaryEntryResponse {
    private String username;
    private List<DiaryEntryResponseData> entries;
}
