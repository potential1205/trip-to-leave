package com.example.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Trip {
    private int tripId; // Auto-increment primary key
    private int memberId; // References members table
    private String title; // Trip title
    private String content; // Markdown formatted travel plan and review
    private LocalDateTime createdAt; // Record creation timestamp
    private LocalDateTime updatedAt; // Last update timestamp
    private int likes; // Number of likes (default: 0)
    private int overview; // Number of views (default: 0)
    private String startAt; // Start date and time of the trip
    private String endAt; // End date and time of the trip
    private MultipartFile coverImage; // URL or path to the cover image
    private String headings;
}
