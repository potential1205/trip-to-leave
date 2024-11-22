package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TripFile {
    private int fileId;
    private int tripId;
    private String fileName; // 파일 이름
    private String filePath; // 파일 경로
    private String contentType; // 파일 타입 (MIME 타입)
    private String fileType;
}
