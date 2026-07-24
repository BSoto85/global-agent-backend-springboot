package com.globalagent.model.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CaseFileDto {

    private Long id;
    private Integer articleId;
    private String articleContent;
    private String articleTitle;
    private String publishDate;
    private String summaryYoung;
    private String summaryOld;
    private Long countryId;
    private String photoUrl;
}
