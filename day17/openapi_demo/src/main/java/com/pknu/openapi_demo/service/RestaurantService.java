package com.pknu.openapi_demo.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pknu.openapi_demo.dto.FoodKrResponse;
import com.pknu.openapi_demo.dto.FoodKrResponse.GetFoodKr;
import com.pknu.openapi_demo.entity.ItemEntity;
import com.pknu.openapi_demo.repository.RestaurantRepository;

import lombok.RequiredArgsConstructor;

import com.pknu.openapi_demo.dto.Item;

@Service
@RequiredArgsConstructor
public class RestaurantService {

    private final RestaurantRepository restaurantRepository;

    // OpenAPI 호출
    public GetFoodKr fetchRestaurants(int pageNo) {
        StringBuffer result = new StringBuffer();

        try {
            StringBuilder urlBuilder = new StringBuilder("https://apis.data.go.kr/6260000/FoodService/getFoodKr");
            urlBuilder.append("?serviceKey=" + "JzmUY2JqiPqaZHmZ7VDke8wMFu3m%2FCXZSUCawmglK99g1cw5ytYYWZ%2F4VmiJz2Wn5MB1aBEA7N0YlXlJz%2B%2FK8A%3D%3D");
            urlBuilder.append("&pageNo=" + pageNo);
            urlBuilder.append("&numOfRows=" + "10");
            urlBuilder.append("&resultType=json");

            URL url = new URL(urlBuilder.toString());
            // System.out.println(url); // OpenAPI 확인용
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); // GET method로 웹사이트 요청

            BufferedReader rd;
            // conn.getResponseCode() 200(OK) 400(NoPage) 500(Server Error)
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            } else { // 아니면 에러
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream())); 
            }

            // 한줄씩 읽어서 문자열버퍼에 할당
            String line;
            while ((line = rd.readLine()) != null) {
                result.append(line + "\n");
            }

            rd.close();
            conn.disconnect();

            // System.out.println(result.toString()); // 결과 확인용
            
            // JSON 문자열 파싱
            ObjectMapper mapper = new ObjectMapper();
            FoodKrResponse response = mapper.readValue(result.toString(), FoodKrResponse.class);

            // 리스트에 담아서 리턴
            //List<Item> foodList = response.getFoodKr.item;
            GetFoodKr rtFoodKr = response.getFoodKr;
            return rtFoodKr;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public int saveRestaurantToDb(int pageNo) {
        GetFoodKr rtFoodKr = fetchRestaurants(pageNo);
        List<Item> foodList = rtFoodKr.item;

        int saveCount = 0;
        
        for (Item item : foodList) {
            if (!restaurantRepository.existsById(item.UC_SEQ)) { // 중복된 키가 없으면
                ItemEntity entity = new ItemEntity();
                entity.setUC_SEQ(item.UC_SEQ);
                entity.setMAIN_TITLE(item.MAIN_TITLE);
                entity.setGUGUN_NM(item.GUGUN_NM);
                entity.setLAT(item.LAT);
                entity.setLNG(item.LNG);
                entity.setPLACE(item.PLACE);
                entity.setTITLE(item.TITLE);
                entity.setSUBTITLE(item.SUBTITLE);
                entity.setADDR1(item.ADDR1);
                entity.setADDR2(item.ADDR2);
                entity.setCNTCT_TEL(item.CNTCT_TEL);
                entity.setHOMEPAGE_URL(item.HOMEPAGE_URL);
                entity.setUSAGE_DAY_WEEK_AND_TIME(item.USAGE_DAY_WEEK_AND_TIME);
                entity.setRPRSNTV_MENU(item.RPRSNTV_MENU);
                entity.setMAIN_IMG_NORMAL(item.MAIN_IMG_NORMAL);
                entity.setMAIN_IMG_THUMB(item.MAIN_IMG_THUMB);
                entity.setITEMCNTNTS(item.ITEMCNTNTS);
                
                restaurantRepository.save(entity);
                saveCount++;
            }
        }
        
        return saveCount;
    }
}
