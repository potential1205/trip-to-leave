package com.example.domain.trip.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TripFileDto {

    private int tripId;
    private int fileId;
    private String filePath;
    private String fileName;
    private String fileType;
    private String contentType;

}
