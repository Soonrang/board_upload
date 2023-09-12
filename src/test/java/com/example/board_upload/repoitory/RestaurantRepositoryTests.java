package com.example.board_upload.repoitory;

import com.example.board_upload.domain.Restaurant;
import com.example.board_upload.repository.RestaurantRepository;
import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.Optional;
import java.util.UUID;

@SpringBootTest
@Log4j2
public class RestaurantRepositoryTests {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Test
    public void testInsert() {
//        LocalTime openingTime = LocalTime.parse("10:10");
//        LocalTime closingTime = LocalTime.parse("22:00");

        Restaurant restaurant = Restaurant.builder()
                .name("인크커피")
                .categories("카페")
                .openingTime("10")
                .closingTime("18")
                .callNumber("02-854-7200")
                .location("서울 금천구 가산디지털2로 127-20")
                .description("커피와 카페, 익숙한 일상의 가치를 다시 보게하다." +
                        "커피부터 빵까지 소흘히 하지 않는 카페, 인크커피입니다." +
                        "도심 속 여유를 즐겨보시길 바랍니다.")
                .build();


            Restaurant result = restaurantRepository.save(restaurant);
    }



    @Test
    public void testInsertWithImages() {

//        LocalTime openingTime = LocalTime.parse("10:10");
//        LocalTime closingTime = LocalTime.parse("22:00");

        Restaurant restaurant= Restaurant.builder()
                .name("인크커피")
                .categories("카페")
                .openingTime("10")
                .closingTime("18")
                .callNumber("02-854-7200")
                .location("서울 금천구 가산디지털2로 127-20")
                .description("커피와 카페, 익숙한 일상의 가치를 다시 보게하다." +
                        "커피부터 빵까지 소흘히 하지 않는 카페, 인크커피입니다." +
                        "도심 속 여유를 즐겨보시길 바랍니다.")
                .build();

        for (int i = 0; i<3; i++) {
            restaurant.addImage(UUID.randomUUID().toString(), "file"+i+".jpg");
        }
        restaurantRepository.save(restaurant);
    }

    @Test
    public void testReadWithImages(){
        Optional<Restaurant> result = restaurantRepository.findByIdWithImages(1L);
        Restaurant restaurant = result.orElseThrow();

        log.info(restaurant);
        log.info("_---------------------");
        log.info(restaurant.getImageSet());
    }

    @Transactional
    @Commit
    @Test
    public void testModifyImages(){
        Optional<Restaurant> result = restaurantRepository.findByIdWithImages(1L);

        Restaurant restaurant = result.orElseThrow();

        // 기존 첨부파일 삭제
        restaurant.clearImages();

        for(int i=0; i<2; i++) {
            restaurant.addImage(UUID.randomUUID().toString(), "updatefile"+i+".jpg");
        }

        restaurantRepository.save(restaurant);
    }

    @Test
    @Transactional
    @Commit
    public void restRemoveAll(){
        Long rno = 1L;
        restaurantRepository.deleteById(rno);
    }
}
