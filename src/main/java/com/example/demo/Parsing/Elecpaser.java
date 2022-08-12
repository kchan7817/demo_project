package com.example.demo.Parsing;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

@RestController
public class Elecpaser {
    @GetMapping("/api/parsing")
    public static void main(String[] args) {
        String result ="";

        try {
            URL url= new URL("https://bigdata.kepco.co.kr/openapi/v1/powerUsage/contractType.do?year=2020"+"&month=11"
            + "&metroCd=11"+"&cityCd=11"+"&cntrCd="+"&apiKey=fU7M3oOZh4r2KIUYLFKea3Lj71V6tL6YOq9lqQL6"
            + "&returnType=json");

            BufferedReader bf;

            bf = new BufferedReader(new InputStreamReader(url.openStream(), "UTF-8"));

            result = bf.readLine();

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject)jsonParser.parse(result);
            JSONArray dataParser = (JSONArray) jsonObject.get("data");

            System.out.println("조회연도 :" + dataParser.get("year"));
            System.out.println("조회월 :" + dataParser.get("month"));
            System.out.println("시도코드 :" + dataParser.get("metro"));
            System.out.println("시군구코드 :" + dataParser.get("city"));
            System.out.println("계약종별 :" + dataParser.get("cntr"));
            System.out.println("고객호수 :" + dataParser.get("custCnt"));
            System.out.println("전력사용량 :" + dataParser.get("powerUsage"));
            System.out.println("전기요금 :" + dataParser.get("bill"));
            System.out.println("평균판매단가 :" + dataParser.get("unitCost"));

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}
