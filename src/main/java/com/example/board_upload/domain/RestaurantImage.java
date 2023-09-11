package com.example.board_upload.domain;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString(exclude = "restaurant")
public class RestaurantImage implements Comparable<RestaurantImage>{

    @Id
    private String uuid;
    private String fileName;
    private int ord;

    @ManyToOne
    private Restaurant restaurant;
    @Override
    public int compareTo(RestaurantImage other) {
        return this.ord - other.ord;
    }

    public void changeRestaurant(Restaurant restaurant){
        this.restaurant = restaurant;
    }


}
