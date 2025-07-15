package com.pknu.openapi_demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.pknu.openapi_demo.entity.ItemEntity;

@Repository
public interface RestaurantRepository extends JpaRepository<ItemEntity, Integer> {

}
