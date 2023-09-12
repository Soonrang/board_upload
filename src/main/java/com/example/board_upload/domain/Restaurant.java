package com.example.board_upload.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "imageSet")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long rno;

    @Column(length=500, nullable = false)
    private String name;

    @Column(length=500, nullable = false)
    private String categories;

    @Column(nullable = false)
    private String openingTime;

    @Column(nullable = false)
    private String closingTime;

    @Column(length=500, nullable = false)
    private String location;

    @Column(length=500, nullable = false)
    private String callNumber;

//    @Column(nullable = false)
//    private String image;

    @Column(length=5000, nullable = false)
    private String description;

    public void change(String name,String categories, String openingTime, String closingTime,
    String callNumber,  String location, String description ) {
        this.name =name;
        this.categories =categories;
        this.openingTime = openingTime;
        this.closingTime = closingTime;
        this.callNumber = callNumber;
        this.location= location;
        this.description = description;
    }

    @OneToMany(mappedBy = "restaurant",
    cascade = {CascadeType.ALL},
    fetch = FetchType.LAZY,
    orphanRemoval = true)
    @Builder.Default
    private Set<RestaurantImage> imageSet = new HashSet<>();

    public void addImage(String uuid, String fileName){
        RestaurantImage restaurantImage = RestaurantImage.builder()
                .uuid(uuid)
                .fileName(fileName)
                .restaurant(this)
                .ord(imageSet.size())
                .build();

        imageSet.add(restaurantImage);

    }
    public void clearImages (){
        imageSet.forEach(restaurantImage -> restaurantImage.changeRestaurant(null));
        this.imageSet.clear();

    }

}
