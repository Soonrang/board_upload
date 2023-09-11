package com.example.board_upload.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RestaurantDTO {

    private Long rno;

    @NotEmpty
    private String name;

    @NotEmpty
    private String categories;

    @NotNull
    private LocalTime openingTime;

    @NotNull
    private LocalTime closingTime;

    @NotEmpty
    private String location;

    @NotEmpty
    private String callNumber;

    @NotEmpty
    private String description;

    private List<String> fileNames;

    private LocalDateTime rRegDate; // 등록날짜
    private LocalDateTime rModDate; // 수정날짜


}
