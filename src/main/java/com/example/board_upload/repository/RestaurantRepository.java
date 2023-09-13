package com.example.board_upload.repository;

import com.example.board_upload.domain.Restaurant;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.Entity;
import java.util.Optional;


public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

    @EntityGraph(attributePaths =  {"imageSet"})
    @Query("select r from Restaurant r where r.rno = :rno")
    Optional<Restaurant> findByIdWithImages(Long rno);
    Restaurant findByNameAndLocation(String name, String location);


}
