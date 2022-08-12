package com.example.demo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

@RestController
public class ApiController {
    @GetMapping("/api")
    public String callApi() throws IOException{
        StringBuilder result = new StringBuilder();

        String urlStr = "https://bigdata.kepco.co.kr/openapi/v1/powerUsage/contractType.do?" +
                "year=2020" +
                "&month=11" +
                "&metroCd=11" +
                "&cityCd=11" +
                "&cntrCd=" +
                "&apiKey=fU7M3oOZh4r2KIUYLFKea3Lj71V6tL6YOq9lqQL6" +
                "&returnType=json";

        URL url = new URL(urlStr);

        HttpURLConnection urlConnection = (HttpURLConnection)  url.openConnection();
        urlConnection.setRequestMethod("GET");

        BufferedReader br;

        br= new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),"UTF-8"));

        String returnLine;

        while((returnLine = br.readLine()) != null){
            result.append(returnLine+"\n\r");
        }

        urlConnection.disconnect();



        return result.toString();


    }
}
