package com.example.board_upload.service;

import com.example.board_upload.domain.Restaurant;
import com.example.board_upload.domain.RestaurantImage;
import com.example.board_upload.dto.PageRequestDTO;
import com.example.board_upload.dto.PageResponseDTO;
import com.example.board_upload.dto.RestaurantDTO;

import java.util.List;
import java.util.stream.Collectors;

public interface RestaurantService {

    Long register(RestaurantDTO restaurantDTO);

    RestaurantDTO readOne(Long rno);

    void modify(RestaurantDTO restaurantDTO);

    void remove(Long rno);

    PageResponseDTO<RestaurantDTO> list(PageRequestDTO pageRequestDTO);

    default Restaurant dtoToEntity(RestaurantDTO restaurantDTO){

        Restaurant restaurant = Restaurant.builder()
                .rno(restaurantDTO.getRno())
                .name(restaurantDTO.getName())
                .openingTime(restaurantDTO.getOpeningTime())
                .closingTime(restaurantDTO.getClosingTime())
                .location(restaurantDTO.getLocation())
                .callNumber(restaurantDTO.getCallNumber())
                .categories(restaurantDTO.getCategories())
                .description(restaurantDTO.getDescription())
                .build();

        if(restaurantDTO.getFileNames() !=null ){
            restaurantDTO.getFileNames().forEach(fileName -> {
                String[] arr = fileName.split("_");
                restaurant.addImage(arr[0], arr[1]);
            });
        }
        return restaurant;
    }

    default RestaurantDTO entityToDTO(Restaurant restaurant) {
        RestaurantDTO restaurantDTO = RestaurantDTO.builder()
                .rno(restaurant.getRno())
                .name(restaurant.getName())
                .openingTime(restaurant.getOpeningTime())
                .closingTime(restaurant.getClosingTime())
                .location(restaurant.getLocation())
                .callNumber(restaurant.getCallNumber())
                .description(restaurant.getDescription())
                .categories(restaurant.getCategories())
                .build();

        List<String> fileNames =
                restaurant.getImageSet().stream().sorted().map(restaurantImage ->
                        restaurantImage.getUuid()+"_"+restaurantImage.getFileName()).collect(Collectors.toList());

        restaurantDTO.setFileNames(fileNames);
        return restaurantDTO;
    }

}
