package dev.proqa.studentmanagementsystem.dto.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PagingHeaders {

    PAGE_SIZE("Page_Size"),
    PAGE_NUMBER("Page_Number"),
    PAGE_OFFSET("Page_Offset"),
    PAGE_TOTAL("Page_Total"),
    COUNT("Count");

    private final String name;
}
