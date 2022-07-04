package dev.proqa.studentmanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PagingResponseDTO {

    private Long count;

    private Long pageNumber;

    private Long pageSize;

    private Long pageOffset;

    private Long pageTotal;

    private List<UserStdDTO> elements;
}
