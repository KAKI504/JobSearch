package org.example.jobsearch.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class AvatarUploadDto {
    private Long userId;
    private MultipartFile avatar;
}