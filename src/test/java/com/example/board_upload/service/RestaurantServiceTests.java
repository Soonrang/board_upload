package com.example.board_upload.service;

import com.example.board_upload.dto.RestaurantDTO;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;
import java.util.Arrays;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class RestaurantServiceTests {

    @Autowired
    private RestaurantService restaurantService;

    @Test
    public void testRegisterWithImages(){
        LocalTime openingTime = LocalTime.parse("10:10");
        LocalTime closingTime = LocalTime.parse("22:00");

        log.info(restaurantService.getClass().getName());

        RestaurantDTO restaurantDTO = RestaurantDTO.builder()
                .name("인크커피")
                .categories("카페")
                .openingTime(openingTime)
                .closingTime(closingTime)
                .callNumber("02-854-7200")
                .location("서울 금천구 가산디지털2로 127-20")
                .description("커피와 카페, 익숙한 일상의 가치를 다시 보게하다." +
                        "커피부터 빵까지 소흘히 하지 않는 카페, 인크커피입니다." +
                        "도심 속 여유를 즐겨보시길 바랍니다.")
                .build();

        restaurantDTO.setFileNames(
                Arrays.asList(
                        UUID.randomUUID()+"_aaa.jpg",
                        UUID.randomUUID()+"_bbb.jpg",
                        UUID.randomUUID()+"_bbb.jpg"
                ));

        Long rno = restaurantService.register(restaurantDTO);

        log.info("rno "+ +rno);
    }

    @Test
    public void testReadAll() {
        Long rno = 2L;
        RestaurantDTO restaurantDTO = restaurantService.readOne(rno);
        log.info(restaurantDTO);
        for(String fileName : restaurantDTO.getFileNames()) {
            log.info(fileName);
        } // end for
    }

    @Test
    public void testModify() {

        LocalTime openingTime = LocalTime.parse("08:00");
        LocalTime closingTime = LocalTime.parse("18:30");
        //변경에 필요한 데이터
        RestaurantDTO restaurantDTO = RestaurantDTO.builder()
                .rno(3L)
                .name("도노커피 한라원앤원타워")
                .categories("카페")
                .openingTime(openingTime)
                .closingTime(closingTime)
                .callNumber("0507-1442-3534")
                .location("서울 금천구 가산디지털2로 101 1층 113호")
                .description("포장, 무선인터넷, 남/녀 화장실 구분")
                .build();
        restaurantDTO.setFileNames(Arrays.asList(UUID.randomUUID()+"_zzz.jpg"));
        restaurantService.modify(restaurantDTO);
    }

    @Test
    public void testRemoveAll() {
        Long rno = 3L;

        restaurantService.remove(rno);
    }


}
