package com.example.board_upload.service;

import com.example.board_upload.domain.Restaurant;
import com.example.board_upload.domain.RestaurantImage;
import com.example.board_upload.dto.*;
import com.example.board_upload.repository.RestaurantRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Log4j2
@RequiredArgsConstructor
@Transactional
public class RestaurantServiceImpl implements RestaurantService {

    private final ModelMapper modelMapper;
    private final RestaurantRepository restaurantRepository;

    @Override
    public Long register(RestaurantDTO restaurantDTO) {

        Restaurant restaurant = dtoToEntity(restaurantDTO);
        Long rno = restaurantRepository.save(restaurant).getRno();
        return rno;
    }

    @Override
    public RestaurantDTO readOne(Long rno) {
        //re_image까지 조인처리되는 findbywithimages()이용
        Optional<Restaurant> result = restaurantRepository.findByIdWithImages(rno);

        Restaurant restaurant = result.orElseThrow();

        RestaurantDTO restaurantDTO = entityToDTO(restaurant);
        return  restaurantDTO;
//
//        Optional<Restaurant> result = restaurantRepository.findById(rno);
//
//        Restaurant restaurant = result.orElseThrow();
//
//        RestaurantDTO restaurantDTO = modelMapper.map(restaurant, RestaurantDTO.class);
//
//        return restaurantDTO;
    }

    @Override
    public void modify(RestaurantDTO restaurantDTO) {
        Optional<Restaurant> result= restaurantRepository.findById(restaurantDTO.getRno());
        Restaurant restaurant = result.orElseThrow();
        restaurant.change(
                restaurantDTO.getName(),
                restaurantDTO.getCategories(),
                restaurantDTO.getOpeningTime(),
                restaurantDTO.getClosingTime(),
                restaurantDTO.getLocation(),
                restaurantDTO.getCallNumber(),
                restaurantDTO.getDescription()
        );

        restaurant.clearImages();

        if(restaurantDTO.getFileNames() != null) {
            for(String fileName : restaurantDTO.getFileNames()) {
                String[] arr = fileName.split("_");
                restaurant.addImage(arr[0], arr[1]);
            }
        }
        restaurantRepository.save(restaurant);
    }

    @Override
    public void remove(Long rno) {
        restaurantRepository.deleteById(rno);
    }

//    @Override
//    public PageResponseDTO<RestaurantDTO> list(PageRequestDTO pageRequestDTO) {
//
//        Pageable pageable = pageRequestDTO.getPageable("rno");
//        Page<Restaurant> result = restaurantRepository.findAll(pageable);
//
//        List<RestaurantDTO> dtoList = result.getContent().stream()
//                .map(restaurant -> modelMapper.map(restaurant,RestaurantDTO.class))
//                        .collect(Collectors.toList());
//
//        List<RestaurantImageDTO> imageDTOS =
//
//
//        return new PageResponseDTO<>(pageRequestDTO, dtoList, (int)result.getTotalElements());
//    }

    @Override
    public PageRequestDTO<RestaurantListAllDTO> lsitWithAll(PageRequestDTO pageRequestDTO) {
        Pageable pageable = pageRequestDTO.getPageable("rno");

        Page<RestaurantListAllDTO> result = restaurantRepository.findByIdWithImages(pageable);

        return PageResponseDTO.<RestaurantListAllDTO>withAll()
                .pageRequestDTO(pageRequestDTO)
                .dtoList(result.getContent())
                .total((int)result.getTotalElements())
                .build();
    }





}
