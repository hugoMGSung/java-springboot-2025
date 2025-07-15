package com.pknu.openapi_demo.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.pknu.openapi_demo.dto.Item;
import com.pknu.openapi_demo.service.RestaurantService;

@RequestMapping("/busan")
@Controller
public class RestaurantController {

    // 실제 OpenAPI 로직을 처리할 서비스 선언
    @Autowired
    private final RestaurantService restaurantService;

    public RestaurantController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/foodkr")
    public String showFoodKr(@RequestParam(value = "pageNo", defaultValue = "1") int pageNo, Model model) {
        // RestaurantService 에서 OpenAPI를 호출, 데이터 가져옴
        List<Item> busanFoodList = restaurantService.fetchRestaurants(pageNo);

        System.out.println(busanFoodList.size());

        // 리스트로 받은 다음 모델에 할당
        model.addAttribute("busanFoodList", busanFoodList);
        model.addAttribute("currentPage", pageNo);

        // Model로 리스트를 넘김
        return "foodkr";  // templates/foodkr.html 리턴
    }
    
}
