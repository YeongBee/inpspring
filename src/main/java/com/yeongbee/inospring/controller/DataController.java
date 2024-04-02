package com.yeongbee.inospring.controller;

import com.yeongbee.inospring.domain.WeatherData;
import com.yeongbee.inospring.service.WeatherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class DataController {

    private final WeatherService weatherService;
    private static long count;

    @GetMapping("/")
    public String getHome(Model model){
        model.addAttribute("weatherData", weatherService.findLatestData());
        return "home";
    }

    @ResponseBody
    @PostMapping("/home")
    public String postData(@RequestBody WeatherData weatherData){

        weatherService.save(weatherData);
        log.info("temp={} hum={}", weatherData.getTemperature(), weatherData.getHumidity());
        return "";
    }

    @GetMapping("/list")
    public String findAll(Model model){
        List<WeatherData> findAll = weatherService.findAll();

        model.addAttribute("all",findAll );
        return "findAll";
    }

    @PostMapping("/list")
    public String reFind( @RequestParam("startDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
                          @RequestParam("endDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate, Model model){

        log.info("classA={}, classb={}", startDate.getClass(), endDate.getClass());

        List<WeatherData> bets = weatherService.findWeatherDataBetweenDate(startDate, endDate);

        model.addAttribute("bet",bets );
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        return "betweenDate";
    }
}
