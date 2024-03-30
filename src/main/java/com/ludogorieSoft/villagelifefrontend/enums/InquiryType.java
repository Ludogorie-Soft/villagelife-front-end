package com.ludogorieSoft.villagelifefrontend.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InquiryType {
    REQUEST_FOR_CONTACT ("inquiryType.request_for_contact"),
    LOOKING_FOR_BETTER_INDICATORS("inquiryType.looking_for_better_indicators"),
    JOB_OPPORTUNITIES("inquiryType.job_opportunities"),
    OTHER("inquiryType.other");
    private final String name;
}
