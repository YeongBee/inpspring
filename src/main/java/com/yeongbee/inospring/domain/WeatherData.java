package com.yeongbee.inospring.domain;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
public class WeatherData {

    @Id
    private long sequence;      //Pk

    private LocalDate date;     //날짜
    private LocalTime time;     //시간

    private double temperature; //온도
    private double humidity;    //시간
}
