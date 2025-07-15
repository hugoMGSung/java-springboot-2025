package com.pknu.openapi_demo.controller;

import org.springframework.web.bind.annotation.RestController;

import com.pknu.openapi_demo.dto.FoodKrResponse.GetFoodKr;
import com.pknu.openapi_demo.service.RestaurantService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequestMapping("/busanApi")
@RestController
@RequiredArgsConstructor
public class RestaurantRestController {

    private final RestaurantService restaurantService;

    @GetMapping("/foodkr")
    public GetFoodKr getFoodKrApi(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo) {
        GetFoodKr rtFoodKr = restaurantService.fetchRestaurants(pageNo);

        return rtFoodKr;
    }    
}
