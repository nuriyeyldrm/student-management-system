package dev.proqa.studentmanagementsystem.dto;

import dev.proqa.studentmanagementsystem.entities.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PagingResponse {

    private Long count;

    private Long pageNumber;

    private Long pageSize;

    private Long pageOffset;

    private Long pageTotal;

    private List<User> elements;
}
