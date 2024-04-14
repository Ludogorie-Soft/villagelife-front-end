package com.ludogorieSoft.villagelifefrontend.dtos;

import com.ludogorieSoft.villagelifefrontend.enums.InquiryType;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class InquiryDTO {
    private Long id;
    private String userName;
    private String email;
    private String userMessage;
    private String mobile;
    private Long villageId;
    private String villageName;
    private InquiryType inquiryType;
    private boolean hasAgreed;
}
