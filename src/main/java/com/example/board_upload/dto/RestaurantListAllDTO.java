package com.example.board_upload.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

public class RestaurantListAllDTO {
    private Long rno;

    private String name;

    private String categories;

    private String openingTime;

    private String closingTime;

    private String location;

    private String callNumber;

    private String description;

    private List<RestaurantImageDTO> fileNames;
}
