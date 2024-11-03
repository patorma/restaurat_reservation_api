package com.edteam.restaurant_reservation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.edteam.restaurant_reservation.domain.entity.Restaurant;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
//JPQL = Java Persistence Query Language lenguaje estructurado de java
 @Query("SELECT r FROM Restaurant r WHERE r.district.name = :districtName")
 Page<Restaurant> findByDistrictName(@Param("districtName") String districName,Pageable pageable);
}
